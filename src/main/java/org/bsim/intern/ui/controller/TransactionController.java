package org.bsim.intern.ui.controller;

import org.bsim.intern.service.iservice.IServiceTransaction;
import org.bsim.intern.shared.dto.TransactionDTO;
import org.bsim.intern.ui.model.request.TransactionRequest;
import org.bsim.intern.ui.model.response.TransactionResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    //constructor ini sama dgn autowired
    private final IServiceTransaction iServiceTransaction;

    public TransactionController(IServiceTransaction iServiceTransaction) {
        this.iServiceTransaction = iServiceTransaction;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<TransactionResponse> getAllTransaction(){
        List<TransactionResponse> value=new ArrayList<>();
        ModelMapper mapper=new ModelMapper();

        List<TransactionDTO> transactionDTO =iServiceTransaction.getAllTransactions();
        for (TransactionDTO dto: transactionDTO){
            value.add(mapper.map(dto,TransactionResponse.class));
        }

        return value;
    }

    @PostMapping(path = "/{walletid}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public TransactionResponse addNewTransaction(@PathVariable String walletid, @RequestBody TransactionRequest transactionRequest){
        ModelMapper mapper=new ModelMapper();

        TransactionDTO transactionDTO=mapper.map(transactionRequest, TransactionDTO.class);

        TransactionDTO storedData= iServiceTransaction.addNewTransaction(walletid, transactionDTO);

        TransactionResponse value=mapper.map(storedData, TransactionResponse.class);
        return value;
    }

    @GetMapping(path = "/{walletid}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<TransactionResponse> getAllTransactionByWalletid(@PathVariable String walletid){
        ModelMapper mapper=new ModelMapper();
        List<TransactionResponse> value= new ArrayList<>();
        List<TransactionDTO> allTransactionData= iServiceTransaction.getAllTransactionsByWalletid(walletid);

        for(TransactionDTO dto: allTransactionData){
            value.add(mapper.map(dto, TransactionResponse.class));
        }
        return value;
    }

    @PutMapping(path= "/{walletid}/{transactionid}",produces={MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public TransactionResponse updateTransactionbyTransactionid(@PathVariable String walletid, @PathVariable String transactionid, @RequestBody TransactionRequest transactionRequest){
        ModelMapper mapper=new ModelMapper();

        TransactionDTO transactionDTO=mapper.map(transactionRequest, TransactionDTO.class);
        TransactionDTO updatedData=iServiceTransaction.updateTransactionByTransactionid(walletid, transactionid, transactionDTO);

        return mapper.map(updatedData, TransactionResponse.class);
    }

    @DeleteMapping(path = "/{walletid}/{transactionid}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public TransactionResponse deleteTransaction(@PathVariable String walletid, @PathVariable String transactionid){

        ModelMapper mapper=new ModelMapper();
        TransactionDTO transactionDTO= iServiceTransaction.deleteTransaction(walletid,transactionid);

        return mapper.map(transactionDTO, TransactionResponse.class);
    }
}
