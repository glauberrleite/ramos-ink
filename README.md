# RAMOS Ink
RAMOS Ink is a tool to convert InkML files received from a Livescribe Smartpen's penlet called HandSpy to image and/or video. This software is a part of the RAMOS system, a bigger software developed in a project that I was part of, and was developed to run independently. The RAMOS system offers some other features, like production samples management and transcription assistant but it was developed by me, my friend Josmário, and concepted by some researchers, so I don't know if I can simply make it public. In other hand, RAMOS Ink is free under GPL v3.

## Building and Distribution

To produce a jar file, there may have alternative ways of doing this, but we used [Eclipse Luna](https://eclipse.org/) and a plugin called [e(fx)clipse](https://www.eclipse.org/efxclipse/index.html) following the steps above:

1.  Within the Project's root directory, locate and open "build.fxbuild" using the IDE;
2.  You may see a option called "Generate ant build.xml only" that should appears as a link;
3.  After the processing the "build" directory is created;
4.  Take a look at the file "build.xml" inside the "build" directory (a comprehensive xml file that may have no problems, some people across the web experienced resources issues but didn't notice something like that in this project until now);
5.  Right click on it and choose "Run As" followed by "Ant Build";
6.  After the procedure the distribution jar is inside the "dist" directory (within "build").

_Notice that the jar file needs the "libs" directory to correctly function! So Distribute the jar file with the "libs" directory in the same path!_  



## License

RAMOS Ink is a free software developed and distributed under the GNU Public License Version 3. In the Project's root directory you can see the LICENSE. 
