pragma solidity ^0.4.19;

/*

Il seguente contratto è stato prodotto in seguito alla lettura di tutorial 
e guide relative a Solidity.
e.g. https://solidity.readthedocs.io/en/v0.4.23/solidity-in-depth.html

Il Solidity presenta strutture analoghe a quelle presenti
in Jave e C; la differenza sostanziale è nell'utilizzo del gas.
È possibile inoltre utilizzare Inline Assembly al fine di ridurre il costo
del gas e per poter accedere alla Ethereum Virtual Machine ad un livello
più basso, tuttavia, ciò redente più complessa la comprensione del codice.

*/

contract myBankAccount { 
    
    /**
     * I mapping possono essere visti come hashtables che sono virtualmente
     * inizializzati in modo tale che ogni possibile chiave esiste e viene
     * mappata su un valore la cui rappresentazione in byte è tutti zero.
     */
    mapping (address => uint) private balances;    

    /**
     *  Inidirizzo del proprietario
     */
    address public owner;

    /**
     * Evento che viene richiamato quando viene effettuato un deposito
     */ 
    event LogDepositMade(address accountAddress, uint amount);

    /**
     * Costruttore
     */ 
    function Constructor() public {
        owner = msg.sender;
    }

    /**
     * Verifica il proprietario del contratto
     */
    modifier modifier_isContractOwner()
    { 
        if (msg.sender != owner)
        {
            revert();       
        }
        _;
    }

    /**
     * Deposita Ether.
     * La keyword payable è richiesta dalla funzione al fine di ricevere Ether
     */
    function deposit() public payable returns (uint) {
        // Richiede che il bilancio del mittente più il valore depositato
        // sia maggiore del bilancio iniziale
        require((balances[msg.sender] + msg.value) >= balances[msg.sender]);
        // Effettua il bilancio
        balances[msg.sender] += msg.value;
        // Richiama l'evento relativo al deposito
        emit LogDepositMade(msg.sender, msg.value); 
        // Ritorna il bilancio corrente
        return balances[msg.sender];
    }

    /**
     *  Ritira Ether
     */
    function withdraw(uint withdrawAmount) public returns (uint remainingBal) {
        // Richiede che la somma ritirata sia inferiore o uguale al bilancio
        // del mittente
        require(withdrawAmount <= balances[msg.sender]);
        // Decrementa il bilancio
        balances[msg.sender] -= withdrawAmount;
        // Trasferisce gli Ether pari alla somma
        msg.sender.transfer(withdrawAmount);
        // Ritorna il bilancio
        return balances[msg.sender];
    }

    /**
     * Verifica il bilancio del mittente
     */ 
    function balance() constant public returns (uint) {
        return balances[msg.sender];
    }

    /**
     * Verifica il bilancio di un account.
     * La keyword external è necessaria per effettuare interazioni con
     * contratti esterni.
     */
    function balanceOf(address addr) constant external returns (uint) {
        return balances[addr];
    }

    /**
     *  Distrugge il contratto
     */
    function kill() public {
        if (msg.sender == owner)
        selfdestruct(owner);
    }

    /**
     *  Trasferisce Ether all'indirizzo destinatario specificato pari
     *  alla somma richiesta
     */
	function donate(address receiver, uint amount) public {
        // Richiede che il bilancio del mittente sia maggiore
        // della somma che deve essere inviata
		if (balances[msg.sender] < amount) return;
        // Decrementa il bilancio del mittente
		balances[msg.sender] -= amount;
        // Incrementa il bilancio del destinatario
		balances[receiver] += amount;
	}
    
    
}