async function converter() {
  const dados = {
    valorInicial: document.getElementById("valorInicial").value,
    moedaInicial: document.getElementById("moedaInicial").value,
    moedaFinal: document.getElementById("moedaFinal").value,
  };

  const resposta = await fetch("http://localhost:8080/conversor", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(dados),
  });

  const resultado = await resposta.text();

  document.getElementById("valorFinal").value = resultado;
}
