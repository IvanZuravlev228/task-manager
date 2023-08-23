package ivan.taskmanager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import java.util.List;
import lombok.Data;

@Data
@Entity
public class Squad {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String title;
    @OneToOne
    private User owner;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Task> tasks;
}
