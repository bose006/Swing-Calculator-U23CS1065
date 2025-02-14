import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class AdvancedSwingCalculator extends JFrame implements ActionListener {
    private JTextField display;
    private JTextArea historyArea;
    private double num1, num2, result, memory;
    private char operator;
    private boolean memoryFlag = false;
    private ArrayList<String> history = new ArrayList<>();

    public AdvancedSwingCalculator() {
        setTitle("Advanced Swing Calculator");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Display Field
        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 20));
        display.setHorizontalAlignment(JTextField.RIGHT);
        add(display, BorderLayout.NORTH);

        // History Panel
        historyArea = new JTextArea(5, 20);
        historyArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(historyArea);
        add(scrollPane, BorderLayout.EAST);

        // Buttons Panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 4, 5, 5));

        String[] buttons = { "7", "8", "9", "/",
                             "4", "5", "6", "*",
                             "1", "2", "3", "-",
                             "C", "0", ".", "+",
                             "log", "exp", "√", "^",
                             "MC", "MR", "M+", "=" };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.addActionListener(this);
            panel.add(button);
        }

        add(panel, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        try {
            if (command.matches("[0-9.]")) {
                // Prevent multiple decimal points in one number
                if (command.equals(".") && display.getText().contains(".")) return;
                display.setText(display.getText() + command);
            } else if (command.equals("C")) {
                // Clear everything
                display.setText("");
                num1 = num2 = result = 0;
                operator = ' ';
            } else if (command.equals("CE")) {
                // Clear current input (not the whole display)
                display.setText("");
            } else if (command.equals("=")) {
                // Perform the calculation
                calculateResult();
            } else if (command.equals("log")) {
                num1 = Double.parseDouble(display.getText());
                result = Math.log10(num1);
                display.setText(String.valueOf(result));
                addToHistory("log(" + num1 + ") = " + result);
            } else if (command.equals("exp")) {
                num1 = Double.parseDouble(display.getText());
                result = Math.exp(num1);
                display.setText(String.valueOf(result));
                addToHistory("exp(" + num1 + ") = " + result);
            } else if (command.equals("√")) {
                num1 = Double.parseDouble(display.getText());
                result = Math.sqrt(num1);
                display.setText(String.valueOf(result));
                addToHistory("√(" + num1 + ") = " + result);
            } else if (command.equals("^")) {
                num1 = Double.parseDouble(display.getText());
                operator = '^';
                display.setText("");
            } else if (command.equals("MC")) {
                memory = 0; // Clear memory
                memoryFlag = false;
                display.setText("Memory Cleared");
            } else if (command.equals("MR")) {
                display.setText(String.valueOf(memory)); // Recall memory
            } else if (command.equals("M+")) {
                memory += Double.parseDouble(display.getText()); // Memory Add
                memoryFlag = true;
                display.setText("Memory Added");
            } else {
                num1 = Double.parseDouble(display.getText());
                operator = command.charAt(0);
                display.setText("");
            }
        } catch (Exception ex) {
            display.setText("Error");
        }
    }

    private void calculateResult() {
        try {
            num2 = Double.parseDouble(display.getText());
            switch (operator) {
                case '+': result = num1 + num2; break;
                case '-': result = num1 - num2; break;
                case '*': result = num1 * num2; break;
                case '/':
                    if (num2 == 0) {
                        throw new ArithmeticException("Division by zero");
                    }
                    result = num1 / num2;
                    break;
                case '^': result = Math.pow(num1, num2); break;
                default: throw new Exception("Invalid Operation");
            }
            display.setText(String.valueOf(result));
            addToHistory(num1 + " " + operator + " " + num2 + " = " + result);
        } catch (Exception ex) {
            display.setText("Error");
        }
    }

    private void addToHistory(String entry) {
        history.add(entry);
        historyArea.append(entry + "\n");
    }

    public static void main(String[] args) {
        AdvancedSwingCalculator calculator = new AdvancedSwingCalculator();
        calculator.setVisible(true);
    }
}
