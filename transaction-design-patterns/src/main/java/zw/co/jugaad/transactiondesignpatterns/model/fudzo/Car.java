package zw.co.jugaad.transactiondesignpatterns.model.fudzo;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.jugaad.transactiondesignpatterns.enums.FuelType;
import zw.co.jugaad.transactiondesignpatterns.enums.Transmission;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "registration_number",length = 7,unique = true)
    @Pattern(regexp = "^[A-Z]{3}+[0-9]{4}+$",message = "invalid.vehicle.registration.number")
    private String registrationNumber;

    @Column(name = "make",length = 30)
    private String make;

    @Column(name = "model",length = 30)
    private String model;

    @Enumerated(EnumType.STRING)
    @Column(name = "transmission")
    private Transmission transmission;

    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type")
    private FuelType fuelType;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE})
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "card_id")
    @JsonBackReference
    private Card card;
}