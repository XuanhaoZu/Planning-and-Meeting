/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UserInterface.TicketManager;

import Model.Event;
import Model.TicketManager.PaymentRecord;
import Model.TicketManager.Ticket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JPanel;

import java.sql.*;  
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author evelynzu
 */
public class TicketGenerate extends javax.swing.JPanel {

    /**
     * Creates new form TicketGenerate
     */
    javax.swing.JPanel CardSequencePanel;
    
    ArrayList<PaymentRecord> paymentRecords;
    DefaultTableModel model;
    TableRowSorter myTableRowSorter;
    PaymentRecord selectedRecord;
//    Event eventExample;
    
    public TicketGenerate(JPanel clp) {
        this.CardSequencePanel = clp;
        
        
        paymentRecords= new ArrayList<>();
        
        initComponents();
        
        
        dbGetPaymentRecord();
        
        
        populatePaymentRecordTable(); 
        sort(model);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblPaymentRecord = new javax.swing.JTable();
        btnGenerateTicket = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        tfSearch = new javax.swing.JTextField();

        tblPaymentRecord.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Record", "Event", "Customer", "State"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPaymentRecord.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPaymentRecordMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPaymentRecord);

        btnGenerateTicket.setText("Generate Ticket");
        btnGenerateTicket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateTicketActionPerformed(evt);
            }
        });

        jButton2.setText("Search");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnGenerateTicket)
                        .addGap(74, 74, 74))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jButton2)
                        .addGap(49, 49, 49))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(tfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(101, 101, 101)
                .addComponent(btnGenerateTicket)
                .addContainerGap(166, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnGenerateTicketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateTicketActionPerformed
        Ticket ticket = new Ticket();
        ticket.setId(selectedRecord.getId()+(int)(Math.random()*100+1));
        ticket.setCustomerId(selectedRecord.getCustomer());
        ticket.setEventId(selectedRecord.getEvent());
        selectedRecord.setState("completed");
        
        try{  
            Class.forName("com.mysql.cj.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/final5100","root","root");  
            //here sonoo is database name, root is username and password  
            String query = " insert into ticket (ticket_id, customer_id, event_id)"
            + " values ("+ticket.getId()+","+ ticket.getCustomerId()+ "," + ticket.getEventId()+")";  
            PreparedStatement preparedStmt = con.prepareStatement(query);             
            preparedStmt.execute();
            con.close();  
            }catch(Exception e){ System.out.println(e);}
            
        try{  
            Class.forName("com.mysql.cj.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/final5100","root","root");  
            Statement stmt=con.createStatement();             
            String sql = "UPDATE record " + "SET state = '" + "completed" + "' WHERE record_id = '" + selectedRecord.getId()+ "';";
            System.out.print(sql);
            stmt.executeUpdate(sql);
            con.close();  
            }catch(Exception e){ System.out.println(e);}  
            
            populatePaymentRecordTable();
            JOptionPane.showMessageDialog(this, "Ticket generated successfully");
    }//GEN-LAST:event_btnGenerateTicketActionPerformed

    private void tblPaymentRecordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPaymentRecordMouseClicked
        int selectedRowIndex = tblPaymentRecord.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) tblPaymentRecord.getModel();
        selectedRecord = (PaymentRecord)model.getValueAt(tblPaymentRecord.convertRowIndexToModel(selectedRowIndex), 0); 
        
    }//GEN-LAST:event_tblPaymentRecordMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
         String text = tfSearch.getText();
        if (text.trim().length() == 0) {
     myTableRowSorter.setRowFilter(null);
  } else {
     myTableRowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
  }
        tblPaymentRecord.getRowSorter();
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGenerateTicket;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPaymentRecord;
    private javax.swing.JTextField tfSearch;
    // End of variables declaration//GEN-END:variables

    public void populatePaymentRecordTable() {
        model = (DefaultTableModel) tblPaymentRecord.getModel();
        model.setRowCount(0);
        for(PaymentRecord pr: paymentRecords) {
            Object[] row = new Object[4];
            row[0] = pr;
            row[1] = pr.getEvent();
            row[2] = pr.getCustomer();
            row[3] = pr.getState();
            model.addRow(row);
        }
    }
    
    public void dbGetPaymentRecord(){
        try{  
            Class.forName("com.mysql.cj.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/final5100","root","root");  
            //here sonoo is database name, root is username and password  
            Statement stmt=con.createStatement();  
            ResultSet rs=stmt.executeQuery("select * from record");  
            
//            while(rs.next())  
//            System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
            while(rs.next()) {
                PaymentRecord paymentRecord = new PaymentRecord();
                paymentRecord.setId(rs.getString("record_id"));
                
                paymentRecord.setEvent(rs.getString("event_id"));
                paymentRecord.setCustomer(rs.getString("customer_id"));
                paymentRecord.setState(rs.getString("state"));
                System.out.println(paymentRecord.getId());
                paymentRecords.add(paymentRecord);
            }

            rs.close();
            con.close();  
            }catch(Exception e){ System.out.println(e);}  
    }
    
    public void sort(DefaultTableModel model) {
        myTableRowSorter = new TableRowSorter(model);
        tblPaymentRecord.setRowSorter(myTableRowSorter);
        
    }

}
