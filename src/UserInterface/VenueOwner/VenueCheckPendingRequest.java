/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UserInterface.VenueOwner;

import Model.Database;
import Model.SponsorRequest;
import Model.Venue;
import Model.VenueRequest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author evelynzu
 */
public class VenueCheckPendingRequest extends javax.swing.JPanel {

    /**
     * Creates new form VenueOwnerCheckPendingRequest
     */
    javax.swing.JPanel CardSequencePanel;
    Venue ven;
    ArrayList<VenueRequest> reqList;
    Database db;
    
    public VenueCheckPendingRequest(JPanel clp, Venue venue) {
        this.CardSequencePanel = clp;
        initComponents();
        
        this.reqList = new ArrayList<>();
        this.ven = venue;
        getAllRequests();
        populateRequestTable();
        db = new Database();
        
        jLabel2.setText(venue.getUser().getName());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblVenueReq = new javax.swing.JTable();
        btnAccept = new javax.swing.JButton();
        btnDecline = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        jLabel1.setText("Welcome, Venue Owner");

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel2.setText("<Name of the venue>");

        tblVenueReq.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Event", "Status", "Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblVenueReq);

        btnAccept.setText("Accept");
        btnAccept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcceptActionPerformed(evt);
            }
        });

        btnDecline.setText("Decline");
        btnDecline.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeclineActionPerformed(evt);
            }
        });

        jButton3.setText("Check Availability");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 602, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(138, 138, 138)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton3)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnAccept)
                                .addGap(196, 196, 196)
                                .addComponent(btnDecline)))))
                .addContainerGap(68, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDecline)
                    .addComponent(btnAccept))
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addContainerGap(160, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAcceptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcceptActionPerformed
        int selectedRow = tblVenueReq.getSelectedRow();
        
        if (selectedRow < 0){
            JOptionPane.showMessageDialog(this, "Please select a row to Accept.");
            return ;
        }
        
        DefaultTableModel model = (DefaultTableModel) tblVenueReq.getModel();
        VenueRequest request = (VenueRequest) model.getValueAt(selectedRow, 0);
        
        String sqlInsert = "insert into transfer (sender, receiver, "
                    + "type, amount, state, event, request_id) values ("+ request.getEventId() +","+ 
                    request.getVenueId() +",'venueBooking'," + request.getAmount()+", 'pending',"
                    + request.getEventId() + ","+ request.getRequestId() +")";
        db.insert(sqlInsert);
        
        String sqlUpdate = "update venue_request set status = 'In-Progress' where request_id ="+ request.getRequestId();
        db.update(sqlUpdate);
        
        JOptionPane.showMessageDialog(this, "Amount transfer initiated.");
        
        getAllRequests();
        populateRequestTable();
    }//GEN-LAST:event_btnAcceptActionPerformed

    private void btnDeclineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeclineActionPerformed
        int selectedRow = tblVenueReq.getSelectedRow();
        
        if (selectedRow < 0){
            JOptionPane.showMessageDialog(this, "Please select a row to Accept.");
            return ;
        }
        
        DefaultTableModel model = (DefaultTableModel) tblVenueReq.getModel();
        SponsorRequest request = (SponsorRequest) model.getValueAt(selectedRow, 0);
        
        String sqlUpdate = "update sponsor_request set status = 'Rejected' where request_id ="+ request.getRequestId();
        db.update(sqlUpdate);
        
        getAllRequests();
        populateRequestTable();
    }//GEN-LAST:event_btnDeclineActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAccept;
    private javax.swing.JButton btnDecline;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblVenueReq;
    // End of variables declaration//GEN-END:variables

    private void getAllRequests() {
        
        try{  
            Class.forName("com.mysql.cj.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/ems_5100","root","root");   
            Statement stmt=con.createStatement();  
//            sponsor id is the same as user id
//            for all the models user id will be the identity
            ResultSet rs=stmt.executeQuery("select * from venue_request where venue_id = " + ven.getUser().getUser_id());  
            
            while(rs.next()) {
                VenueRequest spRequest = new VenueRequest();
                spRequest.setEventId(rs.getInt("event_id"));
                spRequest.setVenueId(rs.getInt("venue_id"));
                spRequest.setStatus(rs.getString("status"));
                spRequest.setAmount(rs.getInt("amount"));
                spRequest.setRequestId(rs.getInt("request_id"));
                reqList.add(spRequest);
            }

            rs.close();
            con.close();  
        }catch(Exception e){ System.out.println(e);}
    }

    private void populateRequestTable() {
        DefaultTableModel model = (DefaultTableModel)tblVenueReq.getModel();
        model.setRowCount(0);
        
        for(VenueRequest s: reqList) {
            Object[] row = new Object[3];
            row[0] = s;
            row[1] = s.getStatus();
            row[2] = s.getAmount();
            model.addRow(row);
        }
    }
}
