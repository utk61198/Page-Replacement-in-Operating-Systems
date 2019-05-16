//Utkarsh Sharma 1710110364
package osgla2;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

public class PageReplacement implements ActionListener {

    JFrame frame = new JFrame();
    JLabel no_of_frames = new JLabel("     No of Frames     ");
    Choice no_of_frames_choice = new Choice();
    JLabel reference_string_label = new JLabel("     Reference String     ");
    JTextField reference_string_field = new JTextField();
    JButton compute = new JButton("Compute");
    JPanel panel1 = new JPanel(new FlowLayout());
    JPanel panel2 = new JPanel(new FlowLayout());
    Font font = new Font("Comic Sans Ms", Font.BOLD, 25);

    JLabel fifo_display = new JLabel("First in first out");
    JLabel lru_display = new JLabel("Least recently used");
    JLabel opt_display = new JLabel("Optimal replacement");

    JLabel no_of_hits1 = new JLabel("No of Hits = ");
    JLabel no_of_hits2 = new JLabel("No of Hits = ");
    JLabel no_of_hits3 = new JLabel("No of Hits = ");
    int[] reference_array;

    HashSet<Integer> fifo_list;
    LinkedList<Integer> fifo_queue;
    int[] fifo_disp_arr;

    HashSet<Integer> lru_list;
    HashMap<Integer, Integer> lru_map;
    int[] lru_disp_arr;

    HashSet<Integer> opt_list;
    HashMap<Integer, Integer> opt_map;
    int[] opt_disp_arr;

    int no_frames;

    JTable fifo;
    JTable lru;
    JTable opt;
    int count = 0;

    boolean flag = true;
    int page_fault = 0;
    JTable reference_string_display1;
    JTable reference_string_display2;
    JTable reference_string_display3;

    public PageReplacement() {
        Color c = frame.getBackground();

        frame.setLayout(null);

        no_of_frames.setBounds(0, 0, 150, 80);
        no_of_frames.setFont(new Font("Arial Rounded MT", Font.BOLD, 15));

        no_of_frames_choice.setBounds(230, 30, 200, 80);

        no_of_frames_choice.addItem("3");
        no_of_frames_choice.addItem("4");
        no_of_frames_choice.addItem("5");
        no_of_frames_choice.addItem("6");
        no_of_frames_choice.addItem("7");

        reference_string_label.setBounds(0, 80, 200, 80);
        reference_string_label.setFont(new Font("Arial Rounded MT", Font.BOLD, 15));

        reference_string_field.setBounds(230, 109, 400, 30);

        compute.setBounds(720, 109, 100, 30);

        fifo_display.setBounds(20, 190, 200, 30);
        fifo_display.setFont(new Font("Arial Rounded MT", Font.BOLD, 15));

        no_of_hits1.setBounds(700, 190, 200, 30);
        no_of_hits1.setFont(new Font("Arial Rounded MT", Font.BOLD, 15));

        lru_display.setBounds(20, 390, 200, 30);
        lru_display.setFont(new Font("Arial Rounded MT", Font.BOLD, 15));

        no_of_hits2.setBounds(700, 390, 200, 30);
        no_of_hits2.setFont(new Font("Arial Rounded MT", Font.BOLD, 15));

        opt_display.setBounds(20, 590, 200, 30);
        opt_display.setFont(new Font("Arial Rounded MT", Font.BOLD, 15));

        no_of_hits3.setBounds(700, 590, 200, 30);
        no_of_hits3.setFont(new Font("Arial Rounded MT", Font.BOLD, 15));

        frame.add(no_of_frames);
        frame.add(no_of_frames_choice);
        frame.add(reference_string_label);
        frame.add(reference_string_field);
        frame.add(compute);

        fifo = new JTable();

        compute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String[] ref = reference_string_field.getText().split(",");
                reference_array = new int[ref.length];
                no_frames = Integer.parseInt(no_of_frames_choice.getSelectedItem());
                fifo_list = new HashSet<Integer>();
                fifo_queue = new LinkedList<Integer>();
                fifo_disp_arr = new int[Integer.parseInt(no_of_frames_choice.getSelectedItem())];
                lru_disp_arr = new int[Integer.parseInt(no_of_frames_choice.getSelectedItem())];

                lru_list = new HashSet<Integer>();
                lru_map = new HashMap<Integer, Integer>();

                opt_disp_arr = new int[Integer.parseInt(no_of_frames_choice.getSelectedItem())];
                opt_list = new HashSet<>();

                for (int i = 0; i < fifo_disp_arr.length; i++) {
                    fifo_disp_arr[i] = -1;
                }

                for (int i = 0; i < lru_disp_arr.length; i++) {
                    lru_disp_arr[i] = -1;
                }

                for (int i = 0; i < opt_disp_arr.length; i++) {
                    opt_disp_arr[i] = -1;
                }

                for (int i = 0; i < reference_array.length; i++) {
                    reference_array[i] = Integer.parseInt(ref[i]);
                }

                if (count == 1) {
                    frame.remove(reference_string_display1);
                    frame.remove(reference_string_display2);
                    frame.remove(reference_string_display3);

                    reference_string_display1 = new JTable(1, ref.length);
                    reference_string_display2 = new JTable(1, ref.length);
                    reference_string_display3 = new JTable(1, ref.length);

                    for (int i = 0; i < reference_array.length; i++) {
                        reference_string_display1.setValueAt(reference_array[i], 0, i);
                        reference_string_display2.setValueAt(reference_array[i], 0, i);
                        reference_string_display3.setValueAt(reference_array[i], 0, i);

                    }

                    frame.remove(fifo);
                    frame.remove(lru);
                    frame.remove(opt);

                    fifo = new JTable(Integer.parseInt(no_of_frames_choice.getSelectedItem()), ref.length);

                    fifo.setBounds(0, 250, 800, 130);
                    fifo_function();

                    lru = new JTable(Integer.parseInt(no_of_frames_choice.getSelectedItem()), ref.length);
                    lru.setBounds(0, 450, 800, 130);
                    lru_function();
                    opt = new JTable(Integer.parseInt(no_of_frames_choice.getSelectedItem()), ref.length);
                    opt.setBounds(0, 650, 800, 130);
                    opt_function();
                    frame.add(fifo_display);
                    frame.add(lru_display);
                    frame.add(opt_display);
                    frame.add(no_of_hits1);
                    frame.add(no_of_hits2);
                    frame.add(no_of_hits3);

                    fifo.setBackground(c);
                    lru.setBackground(c);
                    opt.setBackground(c);
                    fifo.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
                    lru.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
                    opt.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));

                }
                if (count == 0) {
                    reference_string_display1 = new JTable(1, ref.length);
                    reference_string_display2 = new JTable(1, ref.length);
                    reference_string_display3 = new JTable(1, ref.length);

                    for (int i = 0; i < reference_array.length; i++) {
                        reference_string_display1.setValueAt(reference_array[i], 0, i);
                        reference_string_display2.setValueAt(reference_array[i], 0, i);
                        reference_string_display3.setValueAt(reference_array[i], 0, i);

                    }

                    fifo = new JTable(Integer.parseInt(no_of_frames_choice.getSelectedItem()), ref.length);
                    fifo.setBounds(0, 250, 800, 130);
                    fifo_function();

                    lru = new JTable(Integer.parseInt(no_of_frames_choice.getSelectedItem()), ref.length);
                    lru.setBounds(0, 450, 800, 130);
                    lru_function();
                    opt = new JTable(Integer.parseInt(no_of_frames_choice.getSelectedItem()), ref.length);
                    opt.setBounds(0, 650, 800, 130);
                    opt_function();

                    count = 1;
                    frame.add(fifo_display);
                    frame.add(lru_display);
                    frame.add(opt_display);
                    frame.add(no_of_hits1);
                    frame.add(no_of_hits2);
                    frame.add(no_of_hits3);
                    fifo.setBackground(c);
                    lru.setBackground(c);
                    opt.setBackground(c);
                    fifo.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
                    lru.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
                    opt.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));

                }
                reference_string_display1.setBounds(0, 230, 800, 20);
                reference_string_display2.setBounds(0, 430, 800, 20);
                reference_string_display3.setBounds(0, 630, 800, 20);

                frame.add(reference_string_display1);
                frame.add(reference_string_display2);
                frame.add(reference_string_display3);

                frame.add(fifo);
                frame.add(lru);
                frame.add(opt);
                frame.revalidate();
                frame.repaint();
                flag = false;

            }
        });

        frame.setVisible(true);
        frame.setSize(new Dimension(1000, 1000));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
    }

    public void fifo_function() {
        int index = 0;
        page_fault = 0;
        for (int i = 0; i < reference_array.length; i++) {

            if (fifo_list.size() < no_frames) {

                if (!fifo_list.contains(reference_array[i])) {
                    fifo_list.add(reference_array[i]);
                    fifo_queue.add(reference_array[i]);
                    fifo_disp_arr[index] = reference_array[i];
                    index++;
                    page_fault++;
                }

                for (int k = 0; k < fifo_disp_arr.length; k++) {
                    fifo.setValueAt(fifo_disp_arr[k], k, i);
                }

            } else {
                if (fifo_queue.contains(reference_array[i])) {
                    for (int k = 0; k < fifo_disp_arr.length; k++) {
                        fifo.setValueAt(fifo_disp_arr[k], k, i);
                    }

                }

                if (!fifo_queue.contains(reference_array[i])) {
                    int temp_index = -1;
                    page_fault++;
                    int temp = fifo_queue.peek();
                    fifo_queue.poll();
                    for (int j = 0; j < fifo_disp_arr.length; j++) {
                        if (fifo_disp_arr[j] == temp) {
                            temp_index = j;
                        }
                    }

                    fifo_list.remove(temp);
                    fifo_list.add(reference_array[i]);
                    fifo_disp_arr[temp_index] = reference_array[i];
                    fifo_queue.add(reference_array[i]);
                    for (int k = 0; k < fifo_disp_arr.length; k++) {
                        fifo.setValueAt(fifo_disp_arr[k], k, i);
                    }

                }
            }

        }

        no_of_hits1.setText("No of faults = " + page_fault);

        for (int i = 0; i < Integer.parseInt(no_of_frames_choice.getSelectedItem()); i++) {
            for (int j = 0; j < reference_array.length; j++) {
                if (fifo.getValueAt(i, j).equals(new Integer(-1))) {
                    fifo.setValueAt("null", i, j);
                }
            }
        }
    }

    public void lru_function() {
        page_fault = 0;
        int index = 0;
        for (int i = 0; i < reference_array.length; i++) {
            if (lru_list.size() < no_frames) {
                if (!lru_list.contains(reference_array[i])) {
                    lru_list.add(reference_array[i]);
                    lru_disp_arr[index] = reference_array[i];
                    index++;
                    page_fault++;

                }
                lru_map.put(reference_array[i], i);
                for (int k = 0; k < lru_disp_arr.length; k++) {
                    lru.setValueAt(lru_disp_arr[k], k, i);
                }
            } else {

                if (!lru_list.contains(reference_array[i])) {
                    int replace_index = 0;
                    int temp_index = Integer.MAX_VALUE;
                    int temp_val = Integer.MIN_VALUE;
                    Iterator<Integer> loop = lru_list.iterator();
                    while (loop.hasNext()) {
                        int cmp = loop.next();
                        if (lru_map.get(cmp) < temp_index) {
                            temp_index = lru_map.get(cmp);
                            temp_val = cmp;

                        }

                    }
                    for (int j = 0; j < fifo_disp_arr.length; j++) {
                        if (lru_disp_arr[j] == temp_val) {
                            replace_index = j;
                        }
                    }

                    lru_list.remove(temp_val);
                    lru_list.add(reference_array[i]);

                    lru_disp_arr[replace_index] = reference_array[i];
                    page_fault++;

                }
                for (int k = 0; k < lru_disp_arr.length; k++) {
                    lru.setValueAt(lru_disp_arr[k], k, i);
                }
                lru_map.put(reference_array[i], i);
            }
        }

        no_of_hits2.setText("No of faults = " + page_fault);
        for (int i = 0; i < Integer.parseInt(no_of_frames_choice.getSelectedItem()); i++) {
            for (int j = 0; j < reference_array.length; j++) {
                if (lru.getValueAt(i, j).equals(new Integer(-1))) {
                    lru.setValueAt("null", i, j);
                }
            }
        }

    }

    public void opt_function() {
        page_fault = 0;
        int index = 0;
        for (int i = 0; i < reference_array.length; i++) {
            if (opt_list.size() < no_frames) {
                if (!opt_list.contains(reference_array[i])) {
                    opt_list.add(reference_array[i]);
                    opt_disp_arr[index] = reference_array[i];
                    index++;
                    page_fault++;

                }

                for (int k = 0; k < opt_disp_arr.length; k++) {
                    opt.setValueAt(opt_disp_arr[k], k, i);
                }

            } else {
                if (!opt_list.contains(reference_array[i])) {
                    boolean[] check = new boolean[100];
                    int replace_index = 0;
                    int replace_val = 0;
                    int small = Integer.MIN_VALUE;
                    opt_map = new HashMap<Integer, Integer>();
                    for (int k = 0; k < opt_disp_arr.length; k++) {
                        opt_map.put(opt_disp_arr[k], Integer.MAX_VALUE);    //inserting the set values in hashmap
                    }
                    Iterator<Integer> loop = opt_list.iterator();            //finding the last occurences of the values
                    while (loop.hasNext()) {
                        boolean flag = false;
                        int temp_val = loop.next();
                        int temp_index = 0;
                        for (int j = i + 1; j < reference_array.length; j++) {
                            if (reference_array[j] == temp_val && !check[temp_val]) {
                                temp_index = j;
                                flag = true;
                                check[temp_val] = true;
                            }

                        }
                        if (flag) {
                            opt_map.put(temp_val, temp_index);
                        }

                    }
                    for (int j = 0; j < opt_disp_arr.length; j++) {   // finding the value which is far
                        int temp = opt_map.get(opt_disp_arr[j]);
                        if (temp > small) {
                            small = temp;
                            replace_val = opt_disp_arr[j];
                        }

                    }

                    for (int j = 0; j < opt_disp_arr.length; j++) {
                        if (opt_disp_arr[j] == replace_val) {
                            replace_index = j;
                        }
                    }

                    opt_list.remove(replace_val);
                    opt_disp_arr[replace_index] = reference_array[i];
                    opt_list.add(reference_array[i]);
                    page_fault++;

                }
                for (int k = 0; k < opt_disp_arr.length; k++) {
                    opt.setValueAt(opt_disp_arr[k], k, i);
                }
            }

        }

        no_of_hits3.setText("No of faults = " + page_fault);
        for (int i = 0; i < Integer.parseInt(no_of_frames_choice.getSelectedItem()); i++) {
            for (int j = 0; j < reference_array.length; j++) {
                if (opt.getValueAt(i, j).equals(new Integer(-1))) {
                    opt.setValueAt("null", i, j);
                }
            }
        }

    }

    public static void main(String[] args) {
        PageReplacement obj = new PageReplacement();

    }

}



