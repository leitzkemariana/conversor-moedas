package com.mari.conversormoedas;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

@RestController
@CrossOrigin("http://127.0.0.1:5500/")
public class ConversorController {

    @PostMapping("/conversor")
    public String conversor(@RequestBody ConversorModel conversor) {
        Double valorInicial = conversor.getValorInicial();
        String moedaInicial = conversor.getMoedaInicial();
        String moedaFinal = conversor.getMoedaFinal();

        if (moedaInicial.equals(moedaFinal)) {
            return "Erro: moedas iguais";
        }

        if (moedaInicial.equals("Real (BRL)")){
            moedaInicial = "BRL";
        } else if (moedaInicial.equals("Dólar Americano(USD)")) {
            moedaInicial = "USD";
        } else if (moedaInicial.equals("Euro (EUR)")) {
            moedaInicial = "EUR";
        } else if (moedaInicial.equals("Yuan (CNY)")) {
            moedaInicial = "CNY";
        } else if (moedaInicial.equals("Libra Esterlina (GBP)")){
            moedaInicial = "GBP";
        }

        if (moedaFinal.equals("Real (BRL)")){
            moedaFinal = "BRL";
        } else if (moedaFinal.equals("Dólar Americano(USD)")) {
            moedaFinal = "USD";
        } else if (moedaFinal.equals("Euro (EUR)")) {
            moedaFinal = "EUR";
        } else if (moedaFinal.equals("Yuan (CNY)")) {
            moedaFinal = "CNY";
        } else if (moedaFinal.equals("Libra Esterlina (GBP)")){
            moedaFinal = "GBP";
        }

        if ((moedaInicial.equals("GBP") || (moedaInicial.equals("CNY"))) && (moedaFinal.equals("CNY") || moedaFinal.equals("GBP"))) {
            String urlUSD = "https://economia.awesomeapi.com.br/json/last/" + moedaInicial + "-" + "USD";

            RestTemplate restTemplate = new RestTemplate();
            String libraDolar= restTemplate.getForObject(urlUSD, String.class);

            JSONObject json = new JSONObject(libraDolar);
            double taxa = json.getJSONObject(moedaInicial + "USD").getDouble("bid");

            double valorDolar = valorInicial * taxa;
            valorInicial = valorDolar;
            moedaInicial = "USD";

        }

        String url = "https://economia.awesomeapi.com.br/json/last/" + moedaInicial + "-" + moedaFinal;
        RestTemplate restTemplate = new RestTemplate();

        String resposta = restTemplate.getForObject(url, String.class);
        JSONObject json = new JSONObject(resposta);

        String chave = moedaInicial + moedaFinal;
        double taxa = json.getJSONObject(chave).getDouble("bid");
        double valorFinal = valorInicial * taxa;
        return  String.format("%.2f", valorFinal);
    }
}
