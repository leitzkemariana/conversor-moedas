package com.mari.conversormoedas;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import org.json.JSONObject;

@RestController
@CrossOrigin("http://127.0.0.1:5500/")
public class ConversorController {
    @PostMapping("/conversor")
    public String conversor(@RequestBody ConversorModel conversor) {
        String valorInicial = conversor.getValorInicial();
        String moedaInicial = conversor.getMoedaInicial();
        String moedaFinal = conversor.getMoedaFinal();

        if (moedaInicial.equals(moedaFinal)) {
            return "Erro: moedas iguais";
        }

        if (moedaInicial.equals("Real (BRL)")){
            moedaInicial = "BRL";
        } else if (moedaInicial.equals("Dólar (USD)")) {
            moedaInicial = "USD";
        } else if (moedaInicial.equals("Euro (EUR)")) {
            moedaInicial = "EUR";
        }

        if (moedaFinal.equals("Real (BRL)")){
            moedaFinal = "BRL";
        } else if (moedaFinal.equals("Dólar (USD)")) {
            moedaFinal = "USD";
        } else if (moedaFinal.equals("Euro (EUR)")) {
            moedaFinal = "EUR";
        }

        Double valor = 0.0;

        try{
            valor = Double.parseDouble(valorInicial);
        } catch (NumberFormatException e) {
            return  "Erro: formato inválido (use '.')";
        }

        String url = "https://economia.awesomeapi.com.br/json/last/" + moedaInicial + "-" + moedaFinal;
        RestTemplate restTemplate = new RestTemplate();
        String resposta = restTemplate.getForObject(url, String.class);

        JSONObject json = new JSONObject(resposta);
        String chave = moedaInicial + moedaFinal;
        double taxa = json.getJSONObject(chave).getDouble("bid");

        double valorFinal = (valor * taxa);
        return  String.format("%.2f", valorFinal);
    }
}