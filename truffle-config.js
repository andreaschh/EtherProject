//require('dotenv').config();
var HDWalletProvider=require('truffle-hdwallet-provider');

var infura_apikey="5a8ec5d319e7487fb06ca720eb0f348e";

var mnemonic="drill hunt food team moment mistake bird attitude tunnel ecology sister resist";

module.exports = {
networks: {

     development: {
      host: "127.0.0.1",     // Localhost (default: none)
      port: 7545,            // Standard Ethereum port (default: none)
      network_id: "*",

     },
      rinkeby:{
                   provider: function() {
                          return new HDWalletProvider(mnemonic, "https://rinkeby.infura.io/v3/5a8ec5d319e7487fb06ca720eb0f348e");
                         },
                         network_id: 4,
                         gas: 4500000,
                         gasPrice: 45000000000000000,
                  },
     main:{
                    provider: function() {
                      return new HDWalletProvider(mnemonic, "https://mainnet.infura.io/v3/5a8ec5d319e7487fb06ca720eb0f348e");
                             },
                             network_id: 3,
                   gas: 4500000,
                 gasPrice: 45000000000000000,
                  }

       },
    // Another network with more advanced options...
    // advanced: {
    // port: 8777,             // Custom port
    // network_id: 1342,       // Custom network
    // gas: 8500000,           // Gas sent with each transaction (default: ~6700000)
    // gasPrice: 20000000000,  // 20 gwei (in wei) (default: 100 gwei)
    // from: <address>,        // Account to send txs from (default: accounts[0])
    // websocket: true        // Enable EventEmitter interface for web3 (default: false)
    // },
    // Useful for deploying to a public network.
    // NB: It's important to wrap the provider as a function.
    // ropsten: {
    // provider: () => new HDWalletProvider(mnemonic, `https://ropsten.infura.io/v3/YOUR-PROJECT-ID`),
    // network_id: 3,       // Ropsten's id
    // gas: 5500000,        // Ropsten has a lower block limit than mainnet
    // confirmations: 2,    // # of confs to wait between deployments. (default: 0)
    // timeoutBlocks: 200,  // # of blocks before a deployment times out  (minimum/default: 50)
    // skipDryRun: true     // Skip dry run before migrations? (default: false for public nets )
    // },
    // Useful for private networks
    // private: {
    // provider: () => new HDWalletProvider(mnemonic, `https://network.io`),
    // network_id: 2111,   // This network is yours, in the cloud.
    // production: true    // Treats this network as if it was a public net. (default: false)
    // }
    solc: {
        optimizer: {
          enabled: true,
          runs: 200
        }

    }
  };



 // db: {
   // enabled: false
  //}
//};
