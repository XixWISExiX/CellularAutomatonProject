# JAR File Submission

Commit and push a working JAR file to this directory to receive credit.
**Make sure it is named `App.jar`.**

## Generating the JAR file

Once your code is in working order, you can create an executable JAR
file that can be run from the command line. To do so, do the following:

1. Right-click on your project in the _Package Explorer_
1. Click _Export..._
1. Expand _Java > Runnable JAR..._
1. Click _Next_
1. For Launch Configuration, look for `Application - project2-yourusername`
1. For Export Destination, browse to the `dist/` directory of this project,
   enter the name `App.jar`, and click _Save_
1. Click _Finish_
1. If you see a warning saying "This operation repacks referenced libraries,"
   click _OK_
1. If you see a warning saying "JAR export finished with warnings,"
   click _OK_

## Running the JAR file

To try running it in the command line, you can try out the following commands from
the `dist/` directory.

### `java -jar App.jar eca 22 0 1 0001000 3`

```console
$ java -jar App.jar eca 22 0 1 0001000 3
0001000
0011100
0100010
1110111
```

### `java -jar App.jar eca 22 _ % ___%___ 3`

```console
$ java -jar App.jar eca 22 _ % ___%___ 3
___%___
__%%%__
_%___%_
%%%_%%%
```

### `java -jar App.jar tca 22 . O .............O.............. 7`

```console
$ java -jar App.jar tca 22 . O .............O.............. 7
.............O..............
...........OOOOO............
.........OO.O.O.OO..........
.......OOO...O...OOO........
.....OO...OOOOOOO...OO......
...OOOOOOO.O...O.OOOOOOO....
.OO.O...OO.OOOOO.OO...O.OO..
OO..OOOOO.OOO.OOO.OOOOO..OOO
```
