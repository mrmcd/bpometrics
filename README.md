Eve blueprint manufactoring query tool. 

This is a work in progress API for taking a serious of CSV database dumps, and providing an interface where you can query 
what goes into a blueprint construction, what comes out, and eventually the cost and profit. 


work so far:
```
Testing Industry blueprint constructor database.
Loading industry db into memory...done.
Enter the BPO or BPC item id to query materials and output. Enter -1 to exit.
>Blueprint DB ID [1157]: 
Production Report for id=1157: { name=Standard S Blueprint, group=168, volume=0.01 m3, portion=1 }
Materials:
........ id=34: { name=Tritanium, group=18, volume=0.01 m3, portion=1 } * 3
........ id=35: { name=Pyerite, group=18, volume=0.01 m3, portion=1 } * 1
........ id=36: { name=Mexallon, group=18, volume=0.01 m3, portion=1 } * 2
........ id=37: { name=Isogen, group=18, volume=0.01 m3, portion=1 } * 10
Result:
........ id=242: { name=Standard S, group=86, volume=1.0 m3, portion=1 } * 1

>Blueprint DB ID [1157]: 22364
Blueprint Not Found
>Blueprint DB ID [22364]: 30041
Production Report for id=30041: { name=Legion Electronics - Dissolution Sequencer Blueprint, group=973, volume=0.01 m3, portion=1 }
Materials:
........ id=30002: { name=Fullerene Intercalated Sheets, group=964, volume=5.0 m3, portion=1 } * 1
........ id=30464: { name=Metallofullerene Plating, group=964, volume=5.0 m3, portion=1 } * 1
........ id=30466: { name=Electromechanical Interface Nexus, group=964, volume=10.0 m3, portion=1 } * 1
........ id=30474: { name=Nanowire Composites, group=964, volume=5.0 m3, portion=1 } * 1
........ id=30476: { name=Fulleroferrocene Power Conduits, group=964, volume=5.0 m3, portion=1 } * 1
Result:
........ id=30040: { name=Legion Electronics - Dissolution Sequencer, group=955, volume=40.0 m3, portion=1 } * 1

>Blueprint DB ID [30041]: 4313
Production Report for id=4313: { name=Gallente Fuel Block Blueprint, group=1137, volume=0.01 m3, portion=1 }
Materials:
........ id=44: { name=Enriched Uranium, group=1034, volume=1.5 m3, portion=1 } * 4
........ id=3683: { name=Oxygen, group=1042, volume=0.38 m3, portion=1 } * 22
........ id=3689: { name=Mechanical Parts, group=1034, volume=1.5 m3, portion=1 } * 4
........ id=9832: { name=Coolant, group=1034, volume=1.5 m3, portion=1 } * 9
........ id=9848: { name=Robotics, group=1040, volume=6.0 m3, portion=1 } * 1
........ id=16272: { name=Heavy Water, group=423, volume=0.4 m3, portion=1 } * 167
........ id=16273: { name=Liquid Ozone, group=423, volume=0.4 m3, portion=1 } * 167
........ id=17887: { name=Oxygen Isotopes, group=423, volume=0.1 m3, portion=1 } * 444
Result:
........ id=4312: { name=Gallente Fuel Block, group=1136, volume=5.0 m3, portion=40 } * 40

>Blueprint DB ID [4313]: -1

```


TODO: 

* Create classes to query pricing data from eve-central or goonmetrics.
* Implement ME levels according to eve logic.
* Create a "Cost and Profit" report based on the price and production data:
    * Compare cost of importing (including JF cost) versus local markets of input goods.
    * Compare potential revenue of selling finished products locally versus exporting to Jita
    * Estimate of total profit (or loss) when picking the lowest costs for each material and product for local versus import/export. 
    
* Some sort of nice webui on it instead of a straight up java code and command line

Longer Term:
* TE and Profit/Hour values
* System Cost indexes. 
