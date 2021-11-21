package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.exceptions.TransferNotFoundException;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
public class TransferController {

    private TransferDAO transferDAO;

    public TransferController(TransferDAO transferDAO) {
        this.transferDAO = transferDAO;
    }

    @RequestMapping(path="/transfers", method= RequestMethod.POST)
    public Transfer createTransfer(@RequestBody Transfer transfer) {
        return transferDAO.updateBalance(transfer);
    }

    @RequestMapping(path = "/pendingRequests", method = RequestMethod.GET)
    public List<Transfer> getPendingRequests(Principal principal) throws TransferNotFoundException {
        int accountId = transferDAO.findAccountIdByUsername(principal.getName());
        List<Transfer> transfers = transferDAO.pendingRequests(accountId);
        if(transfers == null){
            throw new TransferNotFoundException();
        }
        return transfers;
    }

    @RequestMapping(path="/requestBucks", method= RequestMethod.POST)
    public Transfer createRequestTransfer(@RequestBody Transfer transfer){
        return transferDAO.updateBalance(transfer);
    }

    @RequestMapping(path = "/transferHistories", method = RequestMethod.GET)
    public List<Transfer> getTransferHistory(Principal principal) throws TransferNotFoundException {
        int accountId = transferDAO.findAccountIdByUsername(principal.getName());
        List<Transfer> transfers = transferDAO.transferHistory(accountId);
        if(transfers == null){
            throw new TransferNotFoundException();
        }
        return transfers;
    }

}
