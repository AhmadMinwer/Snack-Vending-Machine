# Snack-Vending-Machine

###code skeleton 


initializeTheMachine() </br>
item = getUserSelection() </br>
if(checkItemCount(item) < 0){ </br>
 ___ItemSoldOutException; </br>
}else{ </br>
___price = getItemPrice(item) </br>
___balance =0 </br>
___while(balance<price){ </br>
______coin = getUserCoin() </br>
______if(!checkCoinType()){ </br>
_________continue() </br>
______}  </br>
_____balance += coin </br>
___} </br>

___item.count-- </br>
___if( (balance - price) > 0){ </br>
______returnChange(); </br>
___} </br>
}
