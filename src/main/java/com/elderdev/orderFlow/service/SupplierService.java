package com.elderdev.orderFlow.service;

import com.elderdev.orderFlow.entity.Supplier;
import com.elderdev.orderFlow.exception.AlreadyExistsException;
import com.elderdev.orderFlow.exception.NotFoundException;
import com.elderdev.orderFlow.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public List<Supplier> findAll(){
        return supplierRepository.findAll();
    }

    public Supplier findById(Long id){
        return supplierRepository.findById(id).orElseThrow(() -> new NotFoundException("Supplier wasn't found"));
    }

    public Supplier create(Supplier supplier) {
        if(supplierRepository.existsByEmail(supplier.getEmail())){
            throw new AlreadyExistsException("This supplier email already exists");
        }
        return supplierRepository.save(supplier);
    }

        public Supplier update(Long id, Supplier supplier){
        var existing = findById(id);
        existing.setEmail(supplier.getEmail());
        existing.setAddress(supplier.getAddress());
        existing.setPhone(supplier.getPhone());
        return supplierRepository.save(existing);
    }

    public void delete(Long id){
        if(supplierRepository.existsById(id)){
            supplierRepository.deleteById(id);
        }else{
            throw new NotFoundException("Id not found");
        }
    }
}
