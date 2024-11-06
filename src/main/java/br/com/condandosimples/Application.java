package br.com.condandosimples;public class Application {import br.com.condandosimples.dao.DespesaDAO;
import br.com.condandosimples.model.Categoria;
import br.com.condandosimples.model.Despesas;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.swing.table.DefaultTableModel;

public class Application extends JFrame {
    private DespesaDAO dao = new DespesaDAO();

    public Application() {
        setTitle("Gerenciador de Despesas");
        setSize(400, 500);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel logo = new JLabel(new ImageIcon("src/path/to/your/logo.png"));
        add(logo, BorderLayout.NORTH);

        JLabel appTitle = new JLabel("Gerenciador de Despesas", JLabel.CENTER);
        appTitle.setFont(new Font("Arial", Font.BOLD, 20));
        add(appTitle, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1));

        JButton btnAdd = new JButton("Adicionar Despesa");
        JButton btnUpdate = new JButton("Atualizar Despesa");
        JButton btnDelete = new JButton("Excluir Despesa");
        JButton btnList = new JButton("Listar Despesas");
        JButton btnExit = new JButton("Sair");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnList);
        buttonPanel.add(btnExit);

        add(buttonPanel, BorderLayout.CENTER);

        btnAdd.addActionListener(e -> adicionarDespesa());
        btnUpdate.addActionListener(e -> atualizarDespesa());
        btnDelete.addActionListener(e -> excluirDespesa());
        btnList.addActionListener(e -> listarDespesas());
        btnExit.addActionListener(e -> System.exit(0));
    }

    private void adicionarDespesa() {
        String descricao = JOptionPane.showInputDialog("Digite a descrição da despesa:");
        String dataStr = JOptionPane.showInputDialog("Digite a data da despesa (no formato yyyy-MM-dd):");
        LocalDate data = parseDate(dataStr);
        String valorStr = JOptionPane.showInputDialog("Digite o valor da despesa:");
        double valor = Double.parseDouble(valorStr);
        String categoriaStr = JOptionPane.showInputDialog("Digite a categoria da despesa (lazer, aluguel, alimentacao, etc.):");

        Categoria categoria = Categoria.OUTRAS;
        try {
            categoria = Categoria.valueOf(categoriaStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Categoria inválida. Usando 'OUTRAS' como padrão.");
        }

        Despesas despesas = new Despesas();
        despesas.setDescricao(descricao);
        despesas.setData(data);
        despesas.setValor(valor);
        despesas.setCategoria(categoria);
        dao.save(despesas);
        JOptionPane.showMessageDialog(this, "Despesa inserida com sucesso!");
    }

    private void atualizarDespesa() {
        String idStr = JOptionPane.showInputDialog("Digite o ID da despesa que deseja atualizar:");
        Long id = Long.parseLong(idStr);
        Optional<Despesas> despesaExistente = dao.findById(id);

        if (despesaExistente.isPresent()) {
            Despesas despesa = despesaExistente.get();
            String novaDescricao = JOptionPane.showInputDialog("Nova descrição (ou pressione Enter para manter):", despesa.getDescricao());
            despesa.setDescricao(novaDescricao.isEmpty() ? despesa.getDescricao() : novaDescricao);

            String novaDataStr = JOptionPane.showInputDialog("Nova data (ou pressione Enter para manter):", despesa.getData());
            if (!novaDataStr.isEmpty()) {
                despesa.setData(parseDate(novaDataStr));
            }

            String novoValorStr = JOptionPane.showInputDialog("Novo valor (ou pressione Enter para manter):", String.valueOf(despesa.getValor()));
            if (!novoValorStr.isEmpty()) {
                despesa.setValor(Double.parseDouble(novoValorStr));
            }

            String novaCategoriaStr = JOptionPane.showInputDialog("Nova categoria (ou pressione Enter para manter):", despesa.getCategoria());
            if (!novaCategoriaStr.isEmpty()) {
                try {
                    despesa.setCategoria(Categoria.valueOf(novaCategoriaStr.toUpperCase()));
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(this, "Categoria inválida. Mantendo a categoria atual.");
                }
            }

            dao.update(despesa);
            JOptionPane.showMessageDialog(this, "Despesa atualizada com sucesso.");
        } else {
            JOptionPane.showMessageDialog(this, "Despesa não encontrada.");
        }
    }

    private void excluirDespesa() {
        String idStr = JOptionPane.showInputDialog("Digite o ID da despesa que deseja excluir:");
        Long id = Long.parseLong(idStr);
        if (dao.delete(id)) {
            JOptionPane.showMessageDialog(this, "Despesa excluída com sucesso.");
        } else {
            JOptionPane.showMessageDialog(this, "Falha ao excluir a despesa.");
        }
    }

    private void listarDespesas() {
        List<Despesas> despesasList = dao.findALL();
        String[] columnNames = {"ID", "Descrição", "Data", "Valor", "Categoria"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Despesas d : despesasList) {
            model.addRow(new Object[]{
                    d.getId(),
                    d.getDescricao(),
                    d.getData(),
                    "R$ " + String.format("%.2f", d.getValor()),
                    d.getCategoria()
            });
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        JOptionPane.showMessageDialog(this, scrollPane, "Lista de Despesas", JOptionPane.PLAIN_MESSAGE);
    }

    private LocalDate parseDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr);
        } catch (Exception e) {
            return LocalDate.now();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Application app = new Application();
            app.setVisible(true);
        });
    }
}

}
