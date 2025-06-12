document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('btnPesquisar').addEventListener('click', function() {
        const campo = document.getElementById('campoPesquisa').value;
        const valor = document.getElementById('valorPesquisa').value;

        if (!campo || !valor) {
            alert('Por favor, selecione um campo e insira um valor para pesquisa.');
            return;
        }

        let url;
        switch(campo) {
            case 'custoPerCapita':
                url = `/fichas/custoPerCapita?custoPerCapita=${valor}`;
                break;
            case 'custoTotal':
                url = `/fichas/custoTotal?custoTotal=${valor}`;
                break;
            case 'por-nome':
                url = `/fichas/por-nome?nome=${encodeURIComponent(valor)}`;
                break;
            case 'por-categoria':
                url = `/fichas/por-categoria?categoria=${encodeURIComponent(valor)}`;
                break;
            case 'por-rendimento':
                url = `/fichas/por-rendimento?rendimento=${valor}`;
                break;
            case 'por-numero':
                url = `/fichas/por-numero?numero=${valor}`;
                break;
            case 'vtc':
                url = `/fichas/por-vtc?vtc=${valor}`;
                break;
            case 'gramasPTN':
                url = `/fichas/por-gramas-ptn?gramasPTN=${valor}`;
                break;
            case 'gramasCHO':
                url = `/fichas/por-gramas-cho?gramasCHO=${valor}`;
                break;
            case 'gramasLIP':
                url = `/fichas/por-gramas-lip?gramasLIP=${valor}`;
                break;
            case 'gramasSodio':
                url = `/fichas/por-gramas-sodio?gramasSodio=${valor}`;
                break;
            case 'gramasSaturada':
                url = `/fichas/por-gramas-saturada?gramasSaturada=${valor}`;
                break;
            case 'status':
                url = `/fichas/por-status?status=${valor}`;
                break;
            default:
                alert('Campo de pesquisa inválido.');
                return;
        }

        window.location.href = url;
    });

    toggleStatusBtn.addEventListener('click', function() {
        const currentStatus = this.getAttribute('data-current-status');
        const newStatus = currentStatus === 'ATIVA' ? 'INATIVA' : 'ATIVA';

        // Atualiza o texto imediatamente
        const span = this.querySelector('span');
        span.textContent = newStatus === 'ATIVA' ? 'Desarquivadas' : 'Arquivadas';

        // Atualiza o atributo de status
        this.setAttribute('data-current-status', newStatus);

        // Navega para atualizar a lista
        window.location.href = `/fichas/por-status?status=${newStatus}`;
    });

    // Toggle Status de Criação (Completa/Incompleta)
    document.getElementById('toggleStatusCriacaoBtn').addEventListener('click', function() {
        const currentStatus = this.getAttribute('data-current-status-criacao');
        const newStatus = currentStatus === 'COMPLETA' ? 'INCOMPLETA' : 'COMPLETA';

        // Redireciona para o endpoint do controller que você me mostrou
        window.location.href = `/fichas/por-statusCriacao?statusCriacao=${newStatus}`;
    });
});