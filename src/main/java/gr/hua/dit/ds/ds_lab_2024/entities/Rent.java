package gr.hua.dit.ds.ds_lab_2024.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "start_date")
    private Date start_date;

    @Column(name = "end_date")
    private Date end_date;

    @Column(name = "payment")
    private float payment;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Date getStart_date() {
        return start_date;
    }
    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }
    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public float getPayment() {
        return payment;
    }
    public void setPayment(float payment) {
        this.payment = payment;
    }

    public Rent(int id, Date start_date, Date end_date, float payment) {
        this.id = id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.payment = payment;
    }
}
