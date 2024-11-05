Documentação Técnica

**Descrição**

O projeto consiste explicar a utilização do conceito e práticas da utilização do programa.


Importações
DAO e Modelos:
DespesaDAO: Interface para acessar o banco de dados e realizar operações CRUD com despesas.
Categoria: Enum para as categorias de despesas, como "lazer", "aluguel", etc.
Despesas: Classe que representa uma despesa, contendo atributos como descrição, data, valor e categoria.
Componentes de Interface Gráfica (Swing):
JFrame, JButton, JLabel, JOptionPane, JPanel, JTable, JScrollPane
Utilidades:
LocalDate: Manipulação de datas.
DefaultTableModel: Modelo de tabela para exibir despesas.
Classe Principal
Application

Extende: JFrame
Função: Interface gráfica de uma aplicação desktop para gerenciar despesas, com funcionalidades para adicionar, atualizar, excluir e listar despesas.
Estrutura e Componentes
Atributos
DespesaDAO dao: Instância do DAO para manipulação dos dados das despesas.
Construtor: Application()
Configuração da Janela: Define título, tamanho, comportamento de fechamento e layout.
Componentes da Interface:
Logo: Exibido na parte superior da janela.
Título: Nome "Gerenciador de Despesas" com fonte e estilo personalizados.
Botões de Ação:
Adicionar Despesa: Abre uma janela para entrada dos detalhes da nova despesa.
Atualizar Despesa: Abre uma janela para atualizar uma despesa existente com base no ID.
Excluir Despesa: Exclui uma despesa específica.
Listar Despesas: Exibe uma tabela com todas as despesas cadastradas.
Sair: Encerra o programa.
Métodos Principais
adicionarDespesa()

Descrição: Método para adicionar uma nova despesa. Solicita ao usuário dados como descrição, data, valor e categoria.
Categoria Padrão: Se a categoria inserida for inválida, a categoria padrão será "OUTRAS".
Persistência: Salva a despesa no banco de dados usando DespesaDAO.save.
atualizarDespesa()

Descrição: Permite a atualização dos dados de uma despesa com base no ID.
Processo: Solicita ao usuário o ID da despesa, busca o registro correspondente, e permite a atualização de cada campo individualmente.
Persistência: Atualiza o registro no banco de dados usando DespesaDAO.update.
excluirDespesa()

Descrição: Exclui uma despesa a partir de um ID fornecido pelo usuário.
Confirmação: Exibe uma mensagem para informar se a exclusão foi bem-sucedida ou se houve falha.
Persistência: Remove a despesa do banco de dados usando DespesaDAO.delete.
listarDespesas()

Descrição: Exibe uma tabela com todas as despesas armazenadas.
Tabela: Cria um modelo de tabela com colunas para ID, Descrição, Data, Valor e Categoria.
Interface: Exibe a tabela em uma janela pop-up com a lista de despesas.
parseDate(String dateStr)

Descrição: Converte uma string em LocalDate. Se o formato for inválido, retorna a data atual.
Método Principal: main
Descrição: Inicia a aplicação usando SwingUtilities.invokeLater para garantir que a interface seja executada na thread de evento do Swing.
Início da Aplicação: Torna a janela da aplicação visível.
Exemplo de Uso
Iniciar Aplicação: Execute o método main para abrir a interface gráfica.
Adicionar Despesa: Clique em "Adicionar Despesa" e preencha as informações solicitadas.
Atualizar Despesa: Clique em "Atualizar Despesa", insira o ID e altere os dados desejados.
Excluir Despesa: Clique em "Excluir Despesa" e insira o ID para remover a despesa.
Listar Despesas: Clique em "Listar Despesas" para visualizar todas as despesas em uma tabela.
Dependências Externas
Imagem: A imagem usada como logo deve estar localizada no caminho "src/path/to/your/logo.png".
Banco de Dados: É necessário que DespesaDAO esteja configurado para acesso aos dados.
POM.XML: driver JBDC para comunicação com banco de dados.
