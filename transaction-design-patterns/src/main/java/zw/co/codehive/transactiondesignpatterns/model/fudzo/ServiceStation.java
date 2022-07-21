package zw.co.codehive.transactiondesignpatterns.model.fudzo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "service_station")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServiceStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "station_name",length = 50,unique = true)
    private String stationName;

    @Column(name = "address",length = 100)
    private String address;

    @Column(name ="contact_number", length = 15)
    private String contactNumber;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    public void addOwner(Owner owner) {
        this.owner = owner;
    }
}