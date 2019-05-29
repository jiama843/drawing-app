# A2

----------
# Overview

This is a drawing program made in Java swing that supports loading, saving, copying and pasting. Hit detection of objects is done by checking each object to see if one has been clicked (most recently created to least recently created).

![](https://d2mxuefqeaa7sj.cloudfront.net/s_CB9B4614C753700AAD7841036603BDBCAFC60EDB3347DD68DE89F7EE31A5EB98_1549810362180_Screen+Shot+2019-02-10+at+9.52.27+AM.png)

# List of Features

**Requirements:** https://www.student.cs.uwaterloo.ca/~cs349/w19/assignments/a2.html

## Loading/Saving Functions
- **File > New**: Creates a new drawing
- **File > Load**: load a drawing from a file. There are sample drawings in **“/a2/drawings”.**
- **File > Save**: save the current drawing to a file.


## Tools
- **Selection tool**: Click on an existing shape to select it. Pressing ESC will clear selection (**ONLY IF** the canvas is in focus). You can furthermore choose a color or a line thickness to change your shape to match it. Fill colors will **NOT** be changed. You can drag shapes to rearrange them if they are selected.
- **Eraser tool**: Click on a shape to erase it
- **Line drawing tool**: Draws a line via dragging (Supports all directions)
- **Circle drawing tool**: Draws a perfect circle - **NOT** a resizable oval (Supports all directions, behaviour is meant to be the same as in google draw)
- **Rectangle tool**: Draws an unfilled rectangle (Supports all directions)
- **A fill tool**: Fills a shape with a chosen color from the **color palette**. Lines are **NOT** fillable


- **Color palette**: Display of 6 colors, which the user can then use to select the **current color.** The current drawing color will be used for any new shapes that are drawn. There is a chooser that will provide more options.


- A **line thickness palette**: Display of 3 line widths that the user can select. Selecting a line width will set the border thickness for any new shapes drawn. Selecting a shape will change the border thickness to reflect the line thickness of that shape.


## Additional Features
- **System-level copy/paste**: Choose **File > copy** to save the current canvas image to clipboard, then you can paste it into any 3rd party application that also supports system-level copy/paste.
# Icon Sources

https://icon-icons.com/icon/select/48233
License:  CC Attribution-ShareAlike 3.0 Unported

https://icon-icons.com/icon/eraser/70168
License:  CC Attribution 3.0 Unported

https://icon-icons.com/icon/rectangle-perspective-in-diagonal-position/74254
License:  CC Attribution 3.0 Unported

https://icon-icons.com/icon/square-rounded-empty-outlined-button-shape/80680
License:  Creative Commons (Attribution 3.0 Unported)

https://icon-icons.com/icon/perfect-circle/53928
License:  CC Attribution 3.0 Unported

https://icon-icons.com/icon/fill/70144
License:  CC Attribution 3.0 Unported


