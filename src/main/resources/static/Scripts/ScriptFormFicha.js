document.addEventListener("DOMContentLoaded", function () {
    let ingredienteCounter = 0;
    const ingredientesAdicionados = [];

    setTimeout(() => {
        calcularPerfilNutricional();
    }, 500);

    window.adicionarIngrediente = function() {
        const select = document.getElementById("ingredienteSelect");
        const ingredienteId = select.value;
        const selectedOption = select.options[select.selectedIndex];

        if (!ingredienteId) return;

        const medidaCaseira = document.getElementById("medidaCaseira").value;
        const pb = parseFloat(document.getElementById("pb").value) || 0;
        const pl = parseFloat(document.getElementById("pl").value) || 0;
        const custoUsado = document.getElementById("custoUsado").value;
        const custoKg = document.getElementById("custoKg").value;

        if (!medidaCaseira || !pb || !pl || !custoUsado || !custoKg) {
            alert("Preencha todos os campos do ingrediente!");
            return;
        }

        const fc = (pl === 0) ? "0" : (pb / pl).toFixed(2);

        ingredientesAdicionados.push(ingredienteId);

        const novoId = "ingrediente_" + ingredienteCounter++;
        const row = document.createElement("tr");
        row.className = "border bg-gray-50 ingrediente-row";
        row.id = novoId;

        row.innerHTML = `
    <td class="px-4 py-2 border border-black">${selectedOption.text}</td>
    <td class="px-4 py-2 border border-black">${medidaCaseira}</td>
    <td class="px-4 py-2 border border-black">${pb}</td>
    <td class="px-4 py-2 border border-black">${pl}</td>
    <td class="px-4 py-2 border border-black">${fc}</td>
    <td class="px-4 py-2 border border-black">${custoKg}</td>
    <td class="px-4 py-2 border border-black">${custoUsado}</td>
    <td class="px-4 py-2 text-center border border-black">
        <button type="button" onclick="removerIngrediente('${novoId}', '${ingredienteId}')"
                class="w-8 h-8 rounded-full border border-black bg-red-900 hover:bg-red-950 text-white flex items-center justify-center mx-auto relative">
              <i class="fa-solid fa-minus text-2xl"></i>
        </button>
    </td>
    <input type="hidden" name="ingredientes[${ingredienteCounter - 1}].ingredienteId" value="${ingredienteId}">
    <input type="hidden" name="ingredientes[${ingredienteCounter - 1}].medidaCaseira" value="${medidaCaseira}">
    <input type="hidden" name="ingredientes[${ingredienteCounter - 1}].pb" value="${pb}">
    <input type="hidden" name="ingredientes[${ingredienteCounter - 1}].pl" value="${pl}">
    <input type="hidden" name="ingredientes[${ingredienteCounter - 1}].fc" value="${fc}">
    <input type="hidden" name="ingredientes[${ingredienteCounter - 1}].custoKg" value="${custoKg}">
    <input type="hidden" name="ingredientes[${ingredienteCounter - 1}].custoUsado" value="${custoUsado}">
    <input type="hidden" class="ingrediente-ptn" value="${selectedOption.dataset.ptn || 0}">
    <input type="hidden" class="ingrediente-cho" value="${selectedOption.dataset.cho || 0}">
    <input type="hidden" class="ingrediente-lip" value="${selectedOption.dataset.lip || 0}">
    <input type="hidden" class="ingrediente-sodio" value="${selectedOption.dataset.sodio || 0}">
    <input type="hidden" class="ingrediente-saturada" value="${selectedOption.dataset.saturada || 0}">
    `;

        document.getElementById("ingredientesAdicionados").appendChild(row);

        select.value = "";
        document.getElementById("medidaCaseira").value = "";
        document.getElementById("pb").value = "";
        document.getElementById("pl").value = "";
        document.getElementById("fc").value = "";
        document.getElementById("custoKg").value = "";
        document.getElementById("custoUsado").value = "";

        select.focus();
        calcularPerfilNutricional();
    };

    window.removerIngredienteExistente = function(ingredienteId) {
        const row = document.querySelector(`.ingrediente-row[data-ingrediente-id="${ingredienteId}"]`);
        if (row) {
            row.remove();
            calcularPerfilNutricional();
        }
    };

    window.removerIngrediente = function(elementId, ingredienteId) {
        document.getElementById(elementId).remove();
        const index = ingredientesAdicionados.indexOf(ingredienteId);
        if (index > -1) {
            ingredientesAdicionados.splice(index, 1);
        }
        calcularPerfilNutricional();
    };

    window.toggleNutricional = function() {
        const div = document.getElementById('perfilNutricional');
        div.classList.toggle('hidden');
    };

    document.getElementById("pb").addEventListener("input", calcularFCAutomatico);
    document.getElementById("pl").addEventListener("input", calcularFCAutomatico);

    function calcularFCAutomatico() {
        const pb = parseFloat(document.getElementById("pb").value) || 0;
        const pl = parseFloat(document.getElementById("pl").value) || 0;
        const fcInput = document.getElementById("fc");

        if (pl === 0) {
            fcInput.value = "0";
        } else {
            fcInput.value = (pb / pl).toFixed(2);
        }
    }


    function calcularPerfilNutricional() {
        const tabelaNutricional = document.getElementById('nutricionalIngredientes');
        tabelaNutricional.innerHTML = '';

        let totalGramasPTN = 0, totalGramasCHO = 0, totalGramasLIP = 0;
        let totalGramasSodio = 0, totalGramasSaturada = 0;
        let totalKcalPTN = 0, totalKcalCHO = 0, totalKcalLIP = 0;

        const rows = document.querySelectorAll('.ingrediente-row, #ingredientesAdicionados tr:not(:last-child)');

        rows.forEach(row => {
            const nome = row.querySelector('td:first-child').textContent;
            const pl = parseFloat(row.querySelector('[name$=".pl"], [th\\:field*="pl"]').value) || 0;

            const ptnPor100g = parseFloat(row.querySelector('.ingrediente-ptn, [th\\:field*="ptn"]').value) || 0;
            const choPor100g = parseFloat(row.querySelector('.ingrediente-cho, [th\\:field*="cho"]').value) || 0;
            const lipPor100g = parseFloat(row.querySelector('.ingrediente-lip, [th\\:field*="lip"]').value) || 0;
            const sodioPor100g = parseFloat(row.querySelector('.ingrediente-sodio, [th\\:field*="sodio"]').value) || 0;
            const saturadaPor100g = parseFloat(row.querySelector('.ingrediente-saturada, [th\\:field*="saturada"]').value) || 0;

            const ptn = (ptnPor100g / 100) * pl;
            const cho = (choPor100g / 100) * pl;
            const lip = (lipPor100g / 100) * pl;
            const sodio = (sodioPor100g / 100) * pl;
            const saturada = (saturadaPor100g / 100) * pl;

            const kcalPTN = ptn * 4;
            const kcalCHO = cho * 4;
            const kcalLIP = lip * 9;

            const nutriRow = document.createElement('tr');
            nutriRow.className = 'border border-black';
            nutriRow.innerHTML = `
            <td class="px-2 py-2 border border-black">${nome}</td>
            <td class="px-2 py-2 border border-black">${pl.toFixed(2)}</td>
            <td class="px-2 py-2 border border-black">${ptn.toFixed(2)}</td>
            <td class="px-2 py-2 border border-black">${cho.toFixed(2)}</td>
            <td class="px-2 py-2 border border-black">${lip.toFixed(2)}</td>
            <td class="px-2 py-2 border border-black">${sodio.toFixed(2)}</td>
            <td class="px-2 py-2 border border-black">${saturada.toFixed(2)}</td>
        `;
            tabelaNutricional.appendChild(nutriRow);

            totalGramasPTN += ptn;
            totalGramasCHO += cho;
            totalGramasLIP += lip;
            totalGramasSodio += sodio;
            totalGramasSaturada += saturada;
            totalKcalPTN += kcalPTN;
            totalKcalCHO += kcalCHO;
            totalKcalLIP += kcalLIP;
        });

        const totalVTC = totalKcalPTN + totalKcalCHO + totalKcalLIP;
        const totalPorcentPTN = totalVTC === 0 ? 0 : (totalKcalPTN / totalVTC) * 100;
        const totalPorcentCHO = totalVTC === 0 ? 0 : (totalKcalCHO / totalVTC) * 100;
        const totalPorcentLIP = totalVTC === 0 ? 0 : (totalKcalLIP / totalVTC) * 100;

        adicionarLinhaTotal('Gramas', tabelaNutricional, totalGramasPTN, totalGramasCHO, totalGramasLIP, totalGramasSodio, totalGramasSaturada);
        adicionarLinhaTotal('Kcal', tabelaNutricional, totalKcalPTN, totalKcalCHO, totalKcalLIP);
        adicionarLinhaTotal('%', tabelaNutricional, totalPorcentPTN, totalPorcentCHO, totalPorcentLIP);

        atualizarCamposNutricionais(totalVTC, totalKcalPTN, totalKcalCHO, totalKcalLIP,
            totalGramasPTN, totalGramasCHO, totalGramasLIP,
            totalGramasSodio, totalGramasSaturada,
            totalPorcentPTN, totalPorcentCHO, totalPorcentLIP);
    }

    function adicionarLinhaTotal(tipo, tabela, ptn, cho, lip, sodio = null, saturada = null) {
        const row = document.createElement('tr');
        row.className = 'border border-black font-bold bg-gray-100';

        let html = `<td class="px-2 py-2 border border-black">${tipo}</td>`;

        if (sodio !== null && saturada !== null) {
            html += `
            <td class="px-2 py-2 border border-black"></td>
            <td class="px-2 py-2 border border-black">${ptn.toFixed(2)}</td>
            <td class="px-2 py-2 border border-black">${cho.toFixed(2)}</td>
            <td class="px-2 py-2 border border-black">${lip.toFixed(2)}</td>
            <td class="px-2 py-2 border border-black">${sodio.toFixed(2)}</td>
            <td class="px-2 py-2 border border-black">${saturada.toFixed(2)}</td>
        `;
        } else {
            html += `
            <td class="px-2 py-2 border border-black"></td>
            <td class="px-2 py-2 border border-black">${ptn.toFixed(2)}</td>
            <td class="px-2 py-2 border border-black">${cho.toFixed(2)}</td>
            <td class="px-2 py-2 border border-black">${lip.toFixed(2)}</td>
            <td class="px-2 py-2 border border-black" colspan="2"></td>
        `;
        }

        row.innerHTML = html;
        tabela.appendChild(row);
    }

    function atualizarCamposNutricionais(vtc, kcalPtn, kcalCho, kcalLip, gramasPtn, gramasCho, gramasLip, gramasSodio, gramasSaturada, porcentPtn, porcentCho, porcentLip) {
        const vtcDisplay = document.getElementById('vtc-display');
        if (vtcDisplay) {
            vtcDisplay.textContent = vtc.toFixed(2);
        }

        const campos = {
            'vtc': vtc,
            'kcalPtn': kcalPtn,
            'kcalCho': kcalCho,
            'kcalLip': kcalLip,
            'gramasPtn': gramasPtn,
            'gramasCho': gramasCho,
            'gramasLip': gramasLip,
            'gramasSodio': gramasSodio,
            'gramasSaturada': gramasSaturada,
            'porcentPtn': porcentPtn,
            'porcentCho': porcentCho,
            'porcentLip': porcentLip
        };

        for (const [id, valor] of Object.entries(campos)) {
            const campo = document.getElementById(id);
            if (campo) {
                campo.value = valor.toFixed(2);
            }
        }
    }

    document.getElementById('form-ficha').addEventListener('submit', function(e) {
        calcularPerfilNutricional();

        if (document.querySelectorAll('.ingrediente-row').length === 0) {
            e.preventDefault();
            alert('Adicione pelo menos um ingrediente antes de enviar o formulário!');
            return;
        }
        if (document.getElementById('vtc').value === '0.00') {
            e.preventDefault();
            alert('Erro no cálculo do perfil nutricional. Verifique os ingredientes!');
            return;
        }
    });
});