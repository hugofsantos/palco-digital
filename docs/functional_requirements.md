# Documento de Requisitos Funcionais (DRF)
**Projeto:** Palco Digital (Plataforma de Transmissão e Apoio Cultural)

**Visão Geral:** Uma plataforma para artistas independentes realizarem transmissões ao vivo com interação em tempo real e monetização direta via ingressos e doações.

---

## Fase 1: Identidade, Acesso e Pagamentos (O Core)

**RF01 - Gestão de Identidade e Perfis**
* O sistema deve permitir o cadastro de usuários únicos via APIs do tipo REST.
* Um usuário deve poder atuar tanto como "Artista" (criador do evento) quanto como "Espectador" (consumidor do evento) sem a necessidade de múltiplas contas.

**RF02 - Controle de Acesso Baseado em Grupos**
* O sistema deve gerenciar permissões através de "Grupos" (ex: MODERADOR, ESPECTADOR_PAGANTE, ARTISTA) em vez de *roles* globais e estáticas.
* A alocação de um usuário a um grupo deve ser contextual à sala/evento (ex: um usuário pode ser moderador em um show, mas espectador comum em outro evento).

**RF03 - Gestão de Apresentações (Palco)**
* O sistema deve permitir que um Artista crie e agende uma Apresentação.
* Uma Apresentação deve possuir estados bem definidos: Agendada, Ao Vivo, Interrompida e Encerrada.

**RF04 - Processamento de Pagamentos e Ingressos**
* O sistema deve integrar-se a um gateway de pagamentos para processar compras de "Ingressos Virtuais" ou "Apoios/Gorjetas".
* O sistema deve receber a confirmação de pagamento via Webhook de forma segura e idempotente (evitando processamento duplicado).
* O sistema deve, de forma assíncrona, liberar o acesso do usuário (adicionando-o ao grupo correto) após a confirmação do pagamento.

---

## Fase 2: Interação em Tempo Real (Chat)

**RF05 - Salas de Bate-Papo ao Vivo**
* O sistema deve prover uma sala de chat em tempo real via WebSockets para cada Apresentação em estado "Ao Vivo".
* O sistema deve suportar múltiplas conexões simultâneas sem bloqueio das APIs REST principais.

**RF06 - Moderação de Chat**
* Usuários pertencentes ao grupo "MODERADOR" daquela sala específica devem ter privilégios para apagar mensagens ou silenciar espectadores.

**RF07 - Notificação de Apoio Financeiro em Tempo Real**
* Quando uma doação ou compra de ingresso ocorrer durante uma live, o sistema de pagamentos (Fase 1) deve notificar o servidor de WebSockets.
* O chat deve exibir uma notificação global automática celebrando o apoio do espectador.

---

## Fase 3: Transmissão de Vídeo (Streaming)

**RF08 - Ingestão de Vídeo**
* O sistema deve ser capaz de receber o fluxo de vídeo e áudio utilizando o protocolo RTSP direto das fontes de transmissão dos artistas (ex: OBS Studio ou softwares similares).

**RF09 - Distribuição de Vídeo**
* O sistema deve converter e distribuir o fluxo de vídeo para os espectadores conectados utilizando protocolos adequados para consumo em navegadores web (como HLS ou WebRTC).
* A latência entre a transmissão do artista e a recepção pelo espectador deve ser minimizada para garantir a sincronia com as interações do chat.