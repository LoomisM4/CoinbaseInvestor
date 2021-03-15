# Usage
Pass the following information through environment variables
- API_KEY
- SECRET_KEY
- PASSPHRASE

You can define investment plans under /etc/coinbase as json-Files like this:
```json
{
    "product": "<valid product id as string e.g. ETH-EUR>",
    "amount": "<amount as decimal e.g. 100.00>",
    "wallet": "<your wallet to buy crypto e.g. EUR-Wallet>",
    "interval": "<interval as string - valid values are: daily, weekly, monthly, yearly>"
}
```
