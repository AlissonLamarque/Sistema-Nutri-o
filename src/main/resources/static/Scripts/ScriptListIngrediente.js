document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('btnPesquisar').addEventListener('click', function() {
        const campo = document.getElementById('campoPesquisa').value;
        const valor = document.getElementById('valorPesquisa').value;

        if (!campo || !valor) {
            alert('Por favor, selecione um campo e insira um valor para pesquisa.');
            return;
        }

        // For numeric fields, validate the input
        const numericFields = ['PTN', 'CHO', 'LIP', 'sodio', 'gorduras'];
        if (numericFields.includes(campo) && isNaN(valor)) {
            alert('Por favor, insira um valor numérico válido.');
            return;
        }

        // Build the URL with correct parameter names
        let url;
        switch(campo) {
            case 'nome':
                url = `/ingredientes/por-nome?nome=${encodeURIComponent(valor)}`;
                break;
            case 'PTN':
                url = `/ingredientes/por-ptn?ptn=${valor}`;
                break;
            case 'CHO':
                url = `/ingredientes/por-cho?cho=${valor}`;
                break;
            case 'LIP':
                url = `/ingredientes/por-lip?lip=${valor}`;
                break;
            case 'sodio':
                url = `/ingredientes/por-sodio?sodio=${valor}`;
                break;
            case 'gorduras':
                url = `/ingredientes/por-gordura-saturada?gorduraSaturada=${valor}`;
                break;
            default:
                alert('Campo de pesquisa inválido.');
                return;
        }

        window.location.href = url;
    });

    toggleStatusBtn2.addEventListener('click', function() {
        const currentStatus = this.getAttribute('data-current-status');
        const newStatus = currentStatus === 'ATIVA' ? 'INATIVA' : 'ATIVA';

        // Atualiza o texto imediatamente
        const span = this.querySelector('span');
        span.textContent = newStatus === 'ATIVA' ? 'Desarquivadas' : 'Arquivadas';

        // Atualiza o atributo de status
        this.setAttribute('data-current-status', newStatus);

        // Navega para atualizar a lista
        window.location.href = `/ingredientes/por-status?status=${newStatus}`;
    });
});