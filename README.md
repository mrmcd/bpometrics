Eve blueprint manufactoring query tool. 

This is a work in progress API for taking a series of CSV database dumps, and providing an interface where you can query what goes into a blueprint construction, what comes out, and eventually the cost and profit. 


work so far:
```
Testing Industry blueprint constructor database.
Loading industry db into memory...done.
Enter the BPO or BPC item id to query materials and output. Enter -1 to exit.
>Blueprint DB ID [4316]: 4316
Production Report for id=4316: { name=Minmatar Fuel Block Blueprint, group=1137, volume=0.01 m3, portion=1 }
Materials:
........ id=44: { name=Enriched Uranium, group=1034, volume=1.5 m3, portion=1 } * 4
........ id=3683: { name=Oxygen, group=1042, volume=0.38 m3, portion=1 } * 22
........ id=3689: { name=Mechanical Parts, group=1034, volume=1.5 m3, portion=1 } * 4
........ id=9832: { name=Coolant, group=1034, volume=1.5 m3, portion=1 } * 9
........ id=9848: { name=Robotics, group=1040, volume=6.0 m3, portion=1 } * 1
........ id=16272: { name=Heavy Water, group=423, volume=0.4 m3, portion=1 } * 167
........ id=16273: { name=Liquid Ozone, group=423, volume=0.4 m3, portion=1 } * 167
........ id=17889: { name=Hydrogen Isotopes, group=423, volume=0.1 m3, portion=1 } * 444
Result:
........ id=4246: { name=Minmatar Fuel Block, group=1136, volume=5.0 m3, portion=40 } * 40

Query url = http://goonmetrics.com/api/price_data/?station_id=61000072&type_id=44,3683,3689,9832,9848,16272,16273,17889,4246,
Query url = http://goonmetrics.com/api/price_data/?station_id=60003760&type_id=44,3683,3689,9832,9848,16272,16273,17889,4246,
******************** COSTS ********************
ITEM: Enriched Uranium * 4
    DEKLEIN: { BUY 17565 @ 8000.0 /  SELL 109707 @ 8300.0 } TOTAL: 33200.0 ISK
    JITA: { BUY 2731365 @ 8300.0 /  SELL 2454993 @ 8589.0 } TOTAL: 36156.0 ISK
    USE: DEKLEIN
ITEM: Oxygen * 22
    DEKLEIN: { BUY 857421 @ 330.0 /  SELL 0 @ 0.0 } TOTAL: 0.0 ISK
    JITA: { BUY 27368691 @ 325.88 /  SELL 15693007 @ 325.95 } TOTAL: 9678.9 ISK
    USE: JITA
ITEM: Mechanical Parts * 4
    DEKLEIN: { BUY 259493 @ 7049.0 /  SELL 30289 @ 8400.0 } TOTAL: 33600.0 ISK
    JITA: { BUY 2155575 @ 7104.0 /  SELL 2479363 @ 7400.0 } TOTAL: 31400.0 ISK
    USE: JITA
ITEM: Coolant * 9
    DEKLEIN: { BUY 142305 @ 7151.0 /  SELL 15101 @ 8000.0 } TOTAL: 72000.0 ISK
    JITA: { BUY 2210184 @ 7351.01 /  SELL 2717397 @ 7731.99 } TOTAL: 73637.91 ISK
    USE: DEKLEIN
ITEM: Robotics * 1
    DEKLEIN: { BUY 18812 @ 53503.0 /  SELL 16421 @ 55000.0 } TOTAL: 55000.0 ISK
    JITA: { BUY 755842 @ 54204.1 /  SELL 682024 @ 57068.98 } TOTAL: 58868.98 ISK
    USE: DEKLEIN
ITEM: Heavy Water * 167
    DEKLEIN: { BUY 25312 @ 125.01 /  SELL 5162377 @ 139.94 } TOTAL: 23369.98 ISK
    JITA: { BUY 54173075 @ 94.18 /  SELL 198428363 @ 98.55 } TOTAL: 36497.85 ISK
    USE: DEKLEIN
ITEM: Liquid Ozone * 167
    DEKLEIN: { BUY 0 @ 0.0 /  SELL 4951450 @ 468.99 } TOTAL: 78321.33 ISK
    JITA: { BUY 89697569 @ 479.45 /  SELL 115308219 @ 492.29 } TOTAL: 102252.43000000001 ISK
    USE: DEKLEIN
ITEM: Hydrogen Isotopes * 444
    DEKLEIN: { BUY 0 @ 0.0 /  SELL 1346215 @ 1049.84 } TOTAL: 466128.95999999996 ISK
    JITA: { BUY 56853827 @ 780.25 /  SELL 91583102 @ 814.0 } TOTAL: 374736.0 ISK
    USE: JITA
******************** REVENUE ********************
ITEM: Minmatar Fuel Block * 40
    DEKLEIN: { BUY 0 @ 0.0 /  SELL 63740 @ 21000.0 } TOTAL: 0.0 ISK
    JITA: { BUY 1525400 @ 18444.01 /  SELL 1035389 @ 19000.88 } TOTAL: 677760.3999999999 ISK
    USE: JITA
>Blueprint DB ID [4316]: 11619
Production Report for id=11619: { name=Co-Processor I Blueprint, group=346, volume=0.01 m3, portion=1 }
Materials:
........ id=34: { name=Tritanium, group=18, volume=0.01 m3, portion=1 } * 2398
........ id=35: { name=Pyerite, group=18, volume=0.01 m3, portion=1 } * 466
........ id=36: { name=Mexallon, group=18, volume=0.01 m3, portion=1 } * 179
........ id=37: { name=Isogen, group=18, volume=0.01 m3, portion=1 } * 1
........ id=38: { name=Nocxium, group=18, volume=0.01 m3, portion=1 } * 4
Result:
........ id=3887: { name=Co-Processor I, group=285, volume=5.0 m3, portion=1 } * 1

Query url = http://goonmetrics.com/api/price_data/?station_id=61000072&type_id=34,35,36,37,38,3887,
Query url = http://goonmetrics.com/api/price_data/?station_id=60003760&type_id=34,35,36,37,38,3887,
******************** COSTS ********************
ITEM: Tritanium * 2398
    DEKLEIN: { BUY 10000000 @ 5.7 /  SELL 205966161 @ 6.15 } TOTAL: 14747.7 ISK
    JITA: { BUY 7485005781 @ 5.18 /  SELL 25484109919 @ 5.46 } TOTAL: 20287.08 ISK
    USE: DEKLEIN
ITEM: Pyerite * 466
    DEKLEIN: { BUY 0 @ 0.0 /  SELL 66310755 @ 13.99 } TOTAL: 6519.34 ISK
    JITA: { BUY 3209968285 @ 10.2 /  SELL 10822903661 @ 10.55 } TOTAL: 6314.3 ISK
    USE: JITA
ITEM: Mexallon * 179
    DEKLEIN: { BUY 41404269 @ 36.01 /  SELL 32694902 @ 53.0 } TOTAL: 9487.0 ISK
    JITA: { BUY 1852050487 @ 45.56 /  SELL 2778494378 @ 46.14 } TOTAL: 8796.06 ISK
    USE: JITA
ITEM: Isogen * 1
    DEKLEIN: { BUY 0 @ 0.0 /  SELL 18097718 @ 130.0 } TOTAL: 130.0 ISK
    JITA: { BUY 234213684 @ 112.55 /  SELL 851180869 @ 116.75 } TOTAL: 119.75 ISK
    USE: JITA
ITEM: Nocxium * 4
    DEKLEIN: { BUY 100000 @ 580.0 /  SELL 1564123 @ 628.98 } TOTAL: 2515.92 ISK
    JITA: { BUY 241440200 @ 582.25 /  SELL 199730534 @ 628.71 } TOTAL: 2526.84 ISK
    USE: DEKLEIN
******************** REVENUE ********************
ITEM: Co-Processor I * 1
    DEKLEIN: { BUY 0 @ 0.0 /  SELL 0 @ 0.0 } TOTAL: 0.0 ISK
    JITA: { BUY 5986 @ 28531.05 /  SELL 2085 @ 44999.93 } TOTAL: 27031.05 ISK
    USE: JITA
>Blueprint DB ID [11619]: -1

```


__NOTE__: The revenue calculations are still very dumb, assuming that you'll always buy the sell and sell to the buy. The data is there though
to write whatever kind of price comparisons you want.  

TODO: 

* -Create classes to query pricing data from eve-central or goonmetrics.-
* Implement ME levels according to eve logic.
* Create a "Cost and Profit" report based on the price and production data:
    * Compare cost of importing (including JF cost) versus local markets of input goods.
    * Compare potential revenue of selling finished products locally versus exporting to Jita
    * Estimate of total profit (or loss) when picking the lowest costs for each material and product for local versus import/export. 
    
* Some sort of nice web ui on it instead of a straight up java code and command line

Longer Term:
* TE and Profit/Hour values
* System Cost indexes. 
