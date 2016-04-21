package calculator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Surface extends JFrame{
	
	private JPanel p_textArea;
	private static JPanel p_normalButtonArea;
	private JTextField textArea;
	
	private String normalButtons =  "←EC±√789()456*x123/%0.+-=";
	private StringBuffer expression = new StringBuffer();
	private boolean first = true;
	private boolean calculated = false;
	
	
	public Surface(String title){
		super(title);
		setLayout(new BorderLayout(5, 5));
		setSize(296, 370);
		
		initViews();
		
//		System.out.println(Postfix.Calculate("-3.123+5+6*(-6+3.48)/(-0.656*0.15193)+(-3.054)*5") + " ");
		
		
	}
	
	private void initViews(){
		initMenu();
		
		p_textArea = new JPanel();
		p_textArea.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		this.add(p_textArea, "North");
		initNormalView();
		
		p_normalButtonArea = new JPanel();
		p_normalButtonArea.setBorder(BorderFactory.createEmptyBorder(0, 13, 15, 13));
		p_normalButtonArea.setLayout(new GridLayout(5, 5, 10, 10));
		this.add(p_normalButtonArea, "Center");
		initButtonView();

	}
	
	private void initMenu(){
		Font font = new Font("宋体", Font.PLAIN, 16);
		
		JMenu modeChoose = new JMenu("查看(V)");
		
		modeChoose.setMnemonic('V');
		JMenuItem normalItem = new JMenuItem("标准型(T)");
		normalItem.setMnemonic('T');
		modeChoose.add(normalItem);
		JMenuItem scienceItem = new JMenuItem("科学型(S)");
		normalItem.setMnemonic('S');
		modeChoose.add(scienceItem);
		
		modeChoose.setFont(font);
		normalItem.setFont(font);
		scienceItem.setFont(font);
		
		JMenu edit = new JMenu("编辑(E)");
		edit.setMnemonic('E');
		JMenuItem copyItem = new JMenuItem("复制(C)");
		copyItem.setMnemonic('C');
		edit.add(copyItem);
		JMenuItem pasteItem = new JMenuItem("粘贴(P)");
		pasteItem.setMnemonic('P');
		edit.add(pasteItem);
		
		edit.setFont(font);
		copyItem.setFont(font);
		pasteItem.setFont(font);
		

		JMenuBar menuBar = new JMenuBar();
		//menuBar.setBackground(Color.white);
		this.setJMenuBar(menuBar);
		menuBar.add(modeChoose);
		menuBar.add(edit);
	}
	
	private void initNormalView(){
		Font font = new Font("宋体", Font.PLAIN, 20);
		textArea = new JTextField();
		textArea.setText("0");
		textArea.setPreferredSize(new Dimension(250, 40));
		textArea.setEditable(false);
		textArea.setBackground(Color.white);
		textArea.setHorizontalAlignment(JTextField.RIGHT);
		textArea.setFont(font);
		p_textArea.add(textArea);
	}
	
	private void initButtonView(){
		Font font = new Font("宋体", Font.PLAIN, 18);
		JButton button;
		ButtonListener buttonListener = new ButtonListener();
		for (int i = 0; i < normalButtons.length(); ++i){
			if ("E".equals(normalButtons.substring(i, i+1)))
				button = new JButton("CE");
			else if ("x".equals(normalButtons.substring(i, i+1)))
				button = new JButton("1/x");
			else
				button = new JButton(normalButtons.substring(i, i+1));
			
			button.setMargin(new Insets(0, 0, 0, 0));
			button.setFont(font);
			button.addActionListener(buttonListener);
			p_normalButtonArea.add(button);
		}
		
	
	}
	
    protected void processWindowEvent(WindowEvent event) {  
        if (event.getID() == WindowEvent.WINDOW_CLOSING){
        	System.out.println("close");
			System.exit(0);
        	return;
        }
        super.processWindowEvent(event);
    }  
    
    private void setResult(){
    	String result;
		if ("".equals(expression.toString())){
			result = "0";
			expression = new StringBuffer();
		}
		else{
			result = String.valueOf(Postfix.Calculate(expression.toString()));
			expression = new StringBuffer(result);
		}
		textArea.setText(result);
    }
    
    private class ButtonListener implements ActionListener{
    	@Override
    	public void actionPerformed(ActionEvent e){
//    		System.out.println(e.getActionCommand());
    		
    		if (first && "=".equals(e.getActionCommand()))
    			return;
    		else if (first){
    			textArea.setText("");
    			expression.delete(0, expression.length());
    			first = false;
//    			System.out.println("aaaaa");
    		}
    		
    		if (calculated && "=".equals(e.getActionCommand()))
    			calculated = false;

    		switch (e.getActionCommand()){
    		case "←":
    			System.out.println(expression.length());
    			if (expression.length() == 0)
    				textArea.setText("0");
    			else if (expression.length() - 1 == 0){
    				expression.deleteCharAt(expression.length()-1);
    				textArea.setText("0");
    			}
    			else if (expression.length() - 1 > 0){
    				expression.deleteCharAt(expression.length()-1);
    				textArea.setText(expression.toString());
    			}
    			break;
    		case "CE":
    		case "C":
    			textArea.setText("0");
    			expression.delete(0, expression.length());
    			first = true;
    			calculated = false;
    			break;
    		case "±":
    		case "%":
    		case "1/x":
    			break;
    		case "=":
//    			System.out.println(expression.toString());
    			
    			if (!calculated){
    				setResult();
    				calculated = true;
    			}
    			first = true;

    			break;
    		default:
//    			System.out.println(e.getActionCommand());
    			expression.append(e.getActionCommand());
    			textArea.setText(expression.toString());
    			break;
    		}
    	}
    }
    
	
    public static void showErrorDialog(){
    	JOptionPane.showMessageDialog(p_normalButtonArea, "计算式有误！");
    }

}
