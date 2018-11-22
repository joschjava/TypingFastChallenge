package gui;

import java.util.ArrayList;

import org.apache.commons.lang3.RandomUtils;

import domain.Language;

public class LanguageChooser {

	private ArrayList<Language> languageList = new ArrayList<Language>();
	private int position = 0;
	
	public LanguageChooser(){
		Language java = new Language();
		java.setConsoleString("java");
		java.setName("Java");
		java.setSourceCode("package org.springframework.boot.cli;\r\n" + 
				"\r\n" + 
				"public final class SpringCli {\r\n" + 
				"\r\n" + 
				"	private SpringCli() {\r\n" + 
				"	}\r\n" + 
				"\r\n" + 
				"	public static void main(String... args) {\r\n" + 
				"		System.setProperty(\"java.awt.headless\", Boolean.toString(true));\r\n" + 
				"		LogbackInitializer.initialize();\r\n" + 
				"\r\n" + 
				"		CommandRunner runner = new CommandRunner(\"spring\");\r\n" + 
				"		ClassUtils.overrideThreadContextClassLoader(createExtendedClassLoader(runner));\r\n" + 
				"		runner.addCommand(new HelpCommand(runner));\r\n" + 
				"		addServiceLoaderCommands(runner);\r\n" + 
				"		runner.addCommand(new ShellCommand());\r\n" + 
				"		runner.addCommand(new HintCommand(runner));\r\n" + 
				"		runner.setOptionCommands(HelpCommand.class, VersionCommand.class);\r\n" + 
				"		runner.setHiddenCommands(HintCommand.class);\r\n" + 
				"\r\n" + 
				"		int exitCode = runner.runAndHandleErrors(args);\r\n" + 
				"		if (exitCode != 0) {\r\n" + 
				"			// If successful, leave it to run in case it's a server app\r\n" + 
				"			System.exit(exitCode);\r\n" + 
				"		}\r\n" + 
				"	}\r\n" + 
				"\r\n" + 
				"	private static void addServiceLoaderCommands(CommandRunner runner) {\r\n" + 
				"		ServiceLoader<CommandFactory> factories = ServiceLoader\r\n" + 
				"				.load(CommandFactory.class);\r\n" + 
				"		for (CommandFactory factory : factories) {\r\n" + 
				"			runner.addCommands(factory.getCommands());\r\n" + 
				"		}\r\n" + 
				"	}\r\n" + 
				"\r\n" + 
				"	private static URLClassLoader createExtendedClassLoader(CommandRunner runner) {\r\n" + 
				"		return new URLClassLoader(getExtensionURLs(), runner.getClass().getClassLoader());\r\n" + 
				"	}\r\n" + 
				"\r\n" + 
				"	private static URL[] getExtensionURLs() {\r\n" + 
				"		List<URL> urls = new ArrayList<>();\r\n" + 
				"		String home = SystemPropertyUtils\r\n" + 
				"				.resolvePlaceholders(\"${spring.home:${SPRING_HOME:.}}\");\r\n" + 
				"		File extDirectory = new File(new File(home, \"lib\"), \"ext\");\r\n" + 
				"		if (extDirectory.isDirectory()) {\r\n" + 
				"			for (File file : extDirectory.listFiles()) {\r\n" + 
				"				if (file.getName().endsWith(\".jar\")) {\r\n" + 
				"					try {\r\n" + 
				"						urls.add(file.toURI().toURL());\r\n" + 
				"					}\r\n" + 
				"					catch (MalformedURLException ex) {\r\n" + 
				"						throw new IllegalStateException(ex);\r\n" + 
				"					}\r\n" + 
				"				}\r\n" + 
				"			}\r\n" + 
				"		}\r\n" + 
				"		return urls.toArray(new URL[0]);\r\n" + 
				"	}\r\n" + 
				"\r\n" + 
				"}");
		languageList.add(java);
		
		Language cpp = new Language();
		cpp.setConsoleString("cpp");
		cpp.setName("C++");
		cpp.setSourceCode("#include <windows.h>  // for MS Windows\r\n" + 
				"#include <GL/glut.h>  // GLUT, include glu.h and gl.h\r\n" + 
				" \r\n" + 
				"/* Global variables */\r\n" + 
				"char title[] = \"3D Shapes\";\r\n" + 
				" \r\n" + 
				"/* Initialize OpenGL Graphics */\r\n" + 
				"void initGL() {\r\n" + 
				"   glClearColor(0.0f, 0.0f, 0.0f, 1.0f); // Set background color to black and opaque\r\n" + 
				"   glClearDepth(1.0f);                   // Set background depth to farthest\r\n" + 
				"   glEnable(GL_DEPTH_TEST);   // Enable depth testing for z-culling\r\n" + 
				"   glDepthFunc(GL_LEQUAL);    // Set the type of depth-test\r\n" + 
				"   glShadeModel(GL_SMOOTH);   // Enable smooth shading\r\n" + 
				"   glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);  // Nice perspective corrections\r\n" + 
				"}\r\n" + 
				" \r\n" + 
				"/* Handler for window-repaint event. Called back when the window first appears and\r\n" + 
				"   whenever the window needs to be re-painted. */\r\n" + 
				"void display() {\r\n" + 
				"   glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // Clear color and depth buffers\r\n" + 
				"   glMatrixMode(GL_MODELVIEW);     // To operate on model-view matrix\r\n" + 
				" \r\n" + 
				"   // Render a color-cube consisting of 6 quads with different colors\r\n" + 
				"   glLoadIdentity();                 // Reset the model-view matrix\r\n" + 
				"   glTranslatef(1.5f, 0.0f, -7.0f);  // Move right and into the screen\r\n" + 
				" \r\n" + 
				"   glBegin(GL_QUADS);                // Begin drawing the color cube with 6 quads\r\n" + 
				"      // Top face (y = 1.0f)\r\n" + 
				"      // Define vertices in counter-clockwise (CCW) order with normal pointing out\r\n" + 
				"      glColor3f(0.0f, 1.0f, 0.0f);     // Green\r\n" + 
				"      glVertex3f( 1.0f, 1.0f, -1.0f);\r\n" + 
				"      glVertex3f(-1.0f, 1.0f, -1.0f);\r\n" + 
				"      glVertex3f(-1.0f, 1.0f,  1.0f);\r\n" + 
				"      glVertex3f( 1.0f, 1.0f,  1.0f);\r\n" + 
				" \r\n" + 
				"      // Bottom face (y = -1.0f)\r\n" + 
				"      glColor3f(1.0f, 0.5f, 0.0f);     // Orange\r\n" + 
				"      glVertex3f( 1.0f, -1.0f,  1.0f);\r\n" + 
				"      glVertex3f(-1.0f, -1.0f,  1.0f);\r\n" + 
				"      glVertex3f(-1.0f, -1.0f, -1.0f);\r\n" + 
				"      glVertex3f( 1.0f, -1.0f, -1.0f);\r\n" + 
				" \r\n" + 
				"      // Front face  (z = 1.0f)\r\n" + 
				"      glColor3f(1.0f, 0.0f, 0.0f);     // Red\r\n" + 
				"      glVertex3f( 1.0f,  1.0f, 1.0f);\r\n" + 
				"      glVertex3f(-1.0f,  1.0f, 1.0f);\r\n" + 
				"      glVertex3f(-1.0f, -1.0f, 1.0f);\r\n" + 
				"      glVertex3f( 1.0f, -1.0f, 1.0f);\r\n" + 
				" \r\n" + 
				"      // Back face (z = -1.0f)\r\n" + 
				"      glColor3f(1.0f, 1.0f, 0.0f);     // Yellow\r\n" + 
				"      glVertex3f( 1.0f, -1.0f, -1.0f);\r\n" + 
				"      glVertex3f(-1.0f, -1.0f, -1.0f);\r\n" + 
				"      glVertex3f(-1.0f,  1.0f, -1.0f);\r\n" + 
				"      glVertex3f( 1.0f,  1.0f, -1.0f);\r\n" + 
				" \r\n" + 
				"      // Left face (x = -1.0f)\r\n" + 
				"      glColor3f(0.0f, 0.0f, 1.0f);     // Blue\r\n" + 
				"      glVertex3f(-1.0f,  1.0f,  1.0f);\r\n" + 
				"      glVertex3f(-1.0f,  1.0f, -1.0f);\r\n" + 
				"      glVertex3f(-1.0f, -1.0f, -1.0f);\r\n" + 
				"      glVertex3f(-1.0f, -1.0f,  1.0f);\r\n" + 
				" \r\n" + 
				"      // Right face (x = 1.0f)\r\n" + 
				"      glColor3f(1.0f, 0.0f, 1.0f);     // Magenta\r\n" + 
				"      glVertex3f(1.0f,  1.0f, -1.0f);\r\n" + 
				"      glVertex3f(1.0f,  1.0f,  1.0f);\r\n" + 
				"      glVertex3f(1.0f, -1.0f,  1.0f);\r\n" + 
				"      glVertex3f(1.0f, -1.0f, -1.0f);\r\n" + 
				"   glEnd();  // End of drawing color-cube\r\n" + 
				" \r\n" + 
				"   // Render a pyramid consists of 4 triangles\r\n" + 
				"   glLoadIdentity();                  // Reset the model-view matrix\r\n" + 
				"   glTranslatef(-1.5f, 0.0f, -6.0f);  // Move left and into the screen\r\n" + 
				" \r\n" + 
				"   glBegin(GL_TRIANGLES);           // Begin drawing the pyramid with 4 triangles\r\n" + 
				"      // Front\r\n" + 
				"      glColor3f(1.0f, 0.0f, 0.0f);     // Red\r\n" + 
				"      glVertex3f( 0.0f, 1.0f, 0.0f);\r\n" + 
				"      glColor3f(0.0f, 1.0f, 0.0f);     // Green\r\n" + 
				"      glVertex3f(-1.0f, -1.0f, 1.0f);\r\n" + 
				"      glColor3f(0.0f, 0.0f, 1.0f);     // Blue\r\n" + 
				"      glVertex3f(1.0f, -1.0f, 1.0f);\r\n" + 
				" \r\n" + 
				"      // Right\r\n" + 
				"      glColor3f(1.0f, 0.0f, 0.0f);     // Red\r\n" + 
				"      glVertex3f(0.0f, 1.0f, 0.0f);\r\n" + 
				"      glColor3f(0.0f, 0.0f, 1.0f);     // Blue\r\n" + 
				"      glVertex3f(1.0f, -1.0f, 1.0f);\r\n" + 
				"      glColor3f(0.0f, 1.0f, 0.0f);     // Green\r\n" + 
				"      glVertex3f(1.0f, -1.0f, -1.0f);\r\n" + 
				" \r\n" + 
				"      // Back\r\n" + 
				"      glColor3f(1.0f, 0.0f, 0.0f);     // Red\r\n" + 
				"      glVertex3f(0.0f, 1.0f, 0.0f);\r\n" + 
				"      glColor3f(0.0f, 1.0f, 0.0f);     // Green\r\n" + 
				"      glVertex3f(1.0f, -1.0f, -1.0f);\r\n" + 
				"      glColor3f(0.0f, 0.0f, 1.0f);     // Blue\r\n" + 
				"      glVertex3f(-1.0f, -1.0f, -1.0f);\r\n" + 
				" \r\n" + 
				"      // Left\r\n" + 
				"      glColor3f(1.0f,0.0f,0.0f);       // Red\r\n" + 
				"      glVertex3f( 0.0f, 1.0f, 0.0f);\r\n" + 
				"      glColor3f(0.0f,0.0f,1.0f);       // Blue\r\n" + 
				"      glVertex3f(-1.0f,-1.0f,-1.0f);\r\n" + 
				"      glColor3f(0.0f,1.0f,0.0f);       // Green\r\n" + 
				"      glVertex3f(-1.0f,-1.0f, 1.0f);\r\n" + 
				"   glEnd();   // Done drawing the pyramid\r\n" + 
				" \r\n" + 
				"   glutSwapBuffers();  // Swap the front and back frame buffers (double buffering)\r\n" + 
				"}\r\n" + 
				" \r\n" + 
				"/* Handler for window re-size event. Called back when the window first appears and\r\n" + 
				"   whenever the window is re-sized with its new width and height */\r\n" + 
				"void reshape(GLsizei width, GLsizei height) {  // GLsizei for non-negative integer\r\n" + 
				"   // Compute aspect ratio of the new window\r\n" + 
				"   if (height == 0) height = 1;                // To prevent divide by 0\r\n" + 
				"   GLfloat aspect = (GLfloat)width / (GLfloat)height;\r\n" + 
				" \r\n" + 
				"   // Set the viewport to cover the new window\r\n" + 
				"   glViewport(0, 0, width, height);\r\n" + 
				" \r\n" + 
				"   // Set the aspect ratio of the clipping volume to match the viewport\r\n" + 
				"   glMatrixMode(GL_PROJECTION);  // To operate on the Projection matrix\r\n" + 
				"   glLoadIdentity();             // Reset\r\n" + 
				"   // Enable perspective projection with fovy, aspect, zNear and zFar\r\n" + 
				"   gluPerspective(45.0f, aspect, 0.1f, 100.0f);\r\n" + 
				"}\r\n" + 
				" \r\n" + 
				"/* Main function: GLUT runs as a console application starting at main() */\r\n" + 
				"int main(int argc, char** argv) {\r\n" + 
				"   glutInit(&argc, argv);            // Initialize GLUT\r\n" + 
				"   glutInitDisplayMode(GLUT_DOUBLE); // Enable double buffered mode\r\n" + 
				"   glutInitWindowSize(640, 480);   // Set the window's initial width & height\r\n" + 
				"   glutInitWindowPosition(50, 50); // Position the window's initial top-left corner\r\n" + 
				"   glutCreateWindow(title);          // Create window with the given title\r\n" + 
				"   glutDisplayFunc(display);       // Register callback handler for window re-paint event\r\n" + 
				"   glutReshapeFunc(reshape);       // Register callback handler for window re-size event\r\n" + 
				"   initGL();                       // Our own OpenGL initialization\r\n" + 
				"   glutMainLoop();                 // Enter the infinite event-processing loop\r\n" + 
				"   return 0;\r\n" + 
				"}");
		languageList.add(cpp);
		
		Language python = new Language();
		python.setConsoleString("py");
		python.setName("Python");
		python.setSourceCode("import os\r\n" + 
				"import glob\r\n" + 
				"import time\r\n" + 
				"from RPLCD import CharLCD\r\n" + 
				"\r\n" + 
				"lcd = CharLCD(cols=16, rows=2, pin_rs=37, pin_e=35, pins_data=[33, 31, 29, 23])\r\n" + 
				"\r\n" + 
				"os.system('modprobe w1-gpio')\r\n" + 
				"os.system('modprobe w1-therm')\r\n" + 
				"\r\n" + 
				"base_dir = '/sys/bus/w1/devices/'\r\n" + 
				"device_folder = glob.glob(base_dir + '28*')[0]\r\n" + 
				"device_file = device_folder + '/w1_slave'\r\n" + 
				"\r\n" + 
				"def read_temp_raw():\r\n" + 
				"    f = open(device_file, 'r')\r\n" + 
				"    lines = f.readlines()\r\n" + 
				"    f.close()\r\n" + 
				"    return lines\r\n" + 
				"\r\n" + 
				"#CELSIUS CALCULATION\r\n" + 
				"def read_temp_c():\r\n" + 
				"    lines = read_temp_raw()\r\n" + 
				"    while lines[0].strip()[-3:] != 'YES':\r\n" + 
				"        time.sleep(0.2)\r\n" + 
				"        lines = read_temp_raw()\r\n" + 
				"    equals_pos = lines[1].find('t=')\r\n" + 
				"    if equals_pos != -1:\r\n" + 
				"        temp_string = lines[1][equals_pos+2:]\r\n" + 
				"        temp_c = int(temp_string) / 1000.0 # TEMP_STRING IS THE SENSOR OUTPUT, MAKE SURE IT'S AN INTEGER TO DO THE MATH\r\n" + 
				"        temp_c = str(round(temp_c, 1)) # ROUND THE RESULT TO 1 PLACE AFTER THE DECIMAL, THEN CONVERT IT TO A STRING\r\n" + 
				"        return temp_c\r\n" + 
				"\r\n" + 
				"#FAHRENHEIT CALCULATION\r\n" + 
				"def read_temp_f():\r\n" + 
				"    lines = read_temp_raw()\r\n" + 
				"    while lines[0].strip()[-3:] != 'YES':\r\n" + 
				"        time.sleep(0.2)\r\n" + 
				"        lines = read_temp_raw()\r\n" + 
				"    equals_pos = lines[1].find('t=')\r\n" + 
				"    if equals_pos != -1:\r\n" + 
				"        temp_string = lines[1][equals_pos+2:]\r\n" + 
				"        temp_f = (int(temp_string) / 1000.0) * 9.0 / 5.0 + 32.0 # TEMP_STRING IS THE SENSOR OUTPUT, MAKE SURE IT'S AN INTEGER TO DO THE MATH\r\n" + 
				"        temp_f = str(round(temp_f, 1)) # ROUND THE RESULT TO 1 PLACE AFTER THE DECIMAL, THEN CONVERT IT TO A STRING\r\n" + 
				"        return temp_f\r\n" + 
				"\r\n" + 
				"while True:\r\n" + 
				"\r\n" + 
				"    lcd.cursor_pos = (0, 0)\r\n" + 
				"    lcd.write_string(\"Temp: \" + read_temp_c() + unichr(223) + \"C\")\r\n" + 
				"    lcd.cursor_pos = (1, 0)\r\n" + 
				"    lcd.write_string(\"Temp: \" + read_temp_f() + unichr(223) + \"F\")");
		languageList.add(python);
		
		
		
//		Language cpp = new Language();
//		cpp.setConsoleString("cpp");
//		cpp.setSourceCode("");
//		languageList.add(cpp);
	}
	
	public Language getFirstLanguage() {
		position  = RandomUtils.nextInt(0, languageList.size());
		return languageList.get(position);
	}
	
	public Language getNext() {
		position = (position+1)%languageList.size();
		return languageList.get(position);
	}
	
	public Language getPrevious() {
		position--;
		if(position<0) {
			position = languageList.size()-1;
		}
		return languageList.get(position);
	}
	
	public Language getCurrent() {
		return languageList.get(position);
	}
	
	public static String prepareLanguage(Language language) {
		return "nano program."+language.getConsoleString();
	}
	
}
