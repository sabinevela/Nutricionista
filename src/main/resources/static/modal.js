document.addEventListener("DOMContentLoaded", function () {
  const modal = document.getElementById("modalReceta");
  const cerrar = document.querySelector(".cerrar");

  document.querySelectorAll(".ver-receta").forEach(boton => {
    boton.addEventListener("click", function () {
      const recetaId = this.getAttribute("data-id");

      fetch(`/api/receta/${recetaId}`)
        .then(response => response.json())
        .then(data => {
          document.getElementById("modalTitulo").textContent = data.titulo;
          document.getElementById("modalDescripcion").textContent = data.descripcion;

          const ingredientes = document.getElementById("modalIngredientes");
          ingredientes.innerHTML = "";
          data.ingredientes.forEach(ing => {
            const li = document.createElement("li");
            li.textContent = ing;
            ingredientes.appendChild(li);
          });

          const pasos = document.getElementById("modalPasos");
          pasos.innerHTML = "";
          data.pasos.forEach(paso => {
            const li = document.createElement("li");
            li.textContent = paso;
            pasos.appendChild(li);
          });

          modal.style.display = "flex";
        });
    });
  });

  cerrar.addEventListener("click", () => {
    modal.style.display = "none";
  });
});
