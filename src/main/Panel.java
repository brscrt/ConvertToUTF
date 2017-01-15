package main;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import file.FileOperations;

public class Panel extends Panel_Ab implements DropTargetListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	File source,destination;
	DropTarget dt;
	JTextField textInput,textOutput;
	final int windowX=540,windowY=200;

	public static void main(String[] args) {
		new Panel().invoke();
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		JFrame f = new JFrame("Convert ANSI To UTF-8");

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(windowX, windowY);
		f.add(this);
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setVisible(true);
	}

	public Panel() {
		super();
		this.setLayout(null);

				
		final JLabel input = new JLabel("Input : ");
		final JLabel output = new JLabel("Output : ");
		final FileOperations fileOperations = new FileOperations();
		textInput = new JTextField();
		textOutput=new JTextField();
		final JFileChooser fileChooser = new JFileChooser();
		final JButton select = new JButton("Select");
		final JButton save = new JButton("Save");
		final JButton convert=new JButton("Convert");
		
		// text.setEditable(false);
		// TransferHandler th=this.getTransferHandler();
		// DropTarget dt = new DropTarget(this, new DropTargetAdapter() {
		//
		// @Override
		// public void drop(DropTargetDropEvent dtde) {
		// // TODO Auto-generated method stub
		// try {
		//
		// source= (File)
		// dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
		//
		// text.setText(source.getName());
		// System.out.println(source.getName());
		// } catch (UnsupportedFlavorException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		// });
		select.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int returnVal = fileChooser.showOpenDialog(select);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					source = fileChooser.getSelectedFile();
					destination = fileChooser.getSelectedFile();
					textInput.setText(fileChooser.getSelectedFile().getName());
					textOutput.setText(fileChooser.getSelectedFile().getName());
				}
			}
		});
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int returnVal = fileChooser.showSaveDialog(select);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					destination=fileChooser.getSelectedFile();
					textOutput.setText(fileChooser.getSelectedFile().getName());
				}
			}
		});
		convert.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {				
					if(source!=null&&destination!=null)
					if(fileOperations.convert(source, destination)){
						JOptionPane.showMessageDialog(convert, "It has been succesfully converted");						
					}else{
						JOptionPane.showMessageDialog(convert,"Could not convert", "Error",JOptionPane.ERROR_MESSAGE);
					}
			}
		});
		textInput.setEditable(false);
		textOutput.setEditable(false);
		
		input.setBounds(20, 30, input.getPreferredSize().width, input.getPreferredSize().height);
		textInput.setBounds(input.getX() + 50, input.getY(), 350, textInput.getPreferredSize().height);
		select.setBounds(textInput.getX() + textInput.getWidth() + 20, textInput.getY(), select.getPreferredSize().width,
				select.getPreferredSize().height);
		output.setBounds(input.getX(), input.getY()+40, output.getPreferredSize().width, output.getPreferredSize().height);
		textOutput.setBounds(output.getX()+50,output.getY(), 350, textOutput.getPreferredSize().height);
		save.setBounds(textOutput.getX() + textOutput.getWidth() + 20, textOutput.getY(), save.getPreferredSize().width,
				save.getPreferredSize().height);
		convert.setBounds(windowX/2-50, output.getY()+50, convert.getPreferredSize().width, convert.getPreferredSize().height);
		dt = new DropTarget(this, this);
		this.add(input);
		this.add(textInput);
		this.add(select);
		this.add(output);
		this.add(textOutput);
		this.add(save);
		this.add(convert);
	}

	public void drop(DropTargetDropEvent dtde) {
		try {
			// Ok, get the dropped object and try to figure out what it is
			Transferable tr = dtde.getTransferable();
			DataFlavor[] flavors = tr.getTransferDataFlavors();
			for (int i = 0; i < flavors.length; i++) {
				System.out.println("Possible flavor: " + flavors[i].getMimeType());
				// Check for file lists specifically
				if (flavors[i].isFlavorJavaFileListType()) {
					// Great! Accept copy drops...
					dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
					textInput.setText("Successful file list drop.\n\n");

					// And add the list of file names to our text area
					@SuppressWarnings("unchecked")
					List<File> list =   (List<File>) tr.getTransferData(flavors[i]);
					for (int j = 0; j < list.size(); j++) {
						textInput.setText(list.get(0).toString());
						source = (File) list.get(0);
						textOutput.setText(list.get(0).toString());
						destination = source;
						// System.out.println(list.get(j));
						// text.append(list.get(j) + "\n");
					}

					// If we made it this far, everything worked.
					dtde.dropComplete(true);
					return;
				}
				// Ok, is it another Java object?
				else if (flavors[i].isFlavorSerializedObjectType()) {
					dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
					textInput.setText("Successful text drop.\n\n");
					// Object o = tr.getTransferData(flavors[i]);
					// text.append("Object: " + o);
					dtde.dropComplete(true);
					return;
				}
				// How about an input stream?
				else if (flavors[i].isRepresentationClassInputStream()) {
					dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
					textInput.setText("Successful text drop.\n\n");
					textInput.read(new InputStreamReader((InputStream) tr.getTransferData(flavors[i])),
							"from system clipboard");
					dtde.dropComplete(true);
					return;
				}
			}
			// Hmm, the user must not have dropped a file list
			System.out.println("Drop failed: " + dtde);
			dtde.rejectDrop();
		} catch (Exception e) {
			e.printStackTrace();
			dtde.rejectDrop();
		}
	}

	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dragExit(DropTargetEvent dte) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dragOver(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub

	}

}
