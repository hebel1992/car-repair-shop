package pl.coderslab.servicestation.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.servicestation.models.Invoice;
import pl.coderslab.servicestation.repositories.InvoiceRepository;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;

    public Invoice findById(Long id) {
        Invoice invoice = invoiceRepository.findById(id).get();
        return invoice;
    }

    public void deleteInvoice(Long id) {
        Invoice invoice = invoiceRepository.findById(id).get();
        invoiceRepository.delete(invoice);
    }
}
