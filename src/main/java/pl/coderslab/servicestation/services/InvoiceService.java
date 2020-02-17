package pl.coderslab.servicestation.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.servicestation.EntityNotFoundException;
import pl.coderslab.servicestation.models.Invoice;
import pl.coderslab.servicestation.repositories.InvoiceRepository;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;

    public Invoice findById(Long id) {
        return invoiceRepository.findById(id).orElseThrow(()->new EntityNotFoundException(id, Invoice.class.getSimpleName()));
    }

    public void deleteInvoice(Long id) {
        Invoice invoice = findById(id);
        invoiceRepository.delete(invoice);
    }
}
