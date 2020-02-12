package pl.coderslab.servicestation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.servicestation.models.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
