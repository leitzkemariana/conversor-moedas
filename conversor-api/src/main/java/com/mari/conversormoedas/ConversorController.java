package com.mari.conversormoedas;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

@RestController
@CrossOrigin("http://127.0.0.1:5500/")
public class ConversorController {

    @PostMapping("/conversor")
    private String resposta (@RequestBody ConversorModel conversor) {
        Double valorInicial = conversor.getValorInicial();
        String moedaInicial = conversor.getMoedaInicial();
        String moedaFinal = conversor.getMoedaFinal();

        if (moedaInicial.equals(moedaFinal)) {
            return "Erro: moedas iguais";
        }

        if (valorInicial == null) {
            valorInicial = 0.0;
        }

        moedaInicial = abreviacao(moedaInicial);
        moedaFinal = abreviacao(moedaFinal);

        double valorFinal = 0.0;

        try{
            valorFinal = conversao(moedaInicial, moedaFinal, valorInicial);
        } catch (Exception e){
            double valorEmDolar = conversao(moedaInicial, "USD", valorInicial);
            valorFinal = conversao("USD", moedaFinal, valorEmDolar);
        }

        return  String.format("%.2f", valorFinal);
    }

    private String abreviacao (String moeda){
        String abr = moeda.substring((moeda.indexOf("(")+1), moeda.indexOf(")"));
        return abr;
    }

    private Double conversao (String moedaInicial, String moedaFinal, Double valorInicial){
        String url = "https://economia.awesomeapi.com.br/json/last/" + moedaInicial + "-" + moedaFinal;
        RestTemplate restTemplate = new RestTemplate();

        String resposta = restTemplate.getForObject(url, String.class);
        JSONObject json = new JSONObject(resposta);

        String chave = moedaInicial + moedaFinal;
        double taxa = json.getJSONObject(chave).getDouble("bid");
        return valorInicial * taxa;
    }
}
