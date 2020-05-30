<%@ page import="java.io.*" %><%@ 
	page import="java.util.*" %><%@ 
	page import="java.awt.*"%><%@ 
	page import="java.awt.image.*"%><%@ 
	page import="se.kransellwennborg.tink.beans.*"%><%@ 
	page import="javax.imageio.*"%><jsp:useBean id="timeEntries" scope="request" class="se.kransellwennborg.tink.beans.TimeEntries" 
	/><jsp:setProperty name="timeEntries" property="dateString" value="${navDate}" 
	/><jsp:setProperty name="timeEntries" property="staffAlias" value="${user.userName}" /><%

int width = 400;
int height = 100;
int HOURS_FROM = 6;
int HOURS_TO = 20;
ArrayList<TimeEntry> entries = timeEntries.getTimeEntries();


Color background = new Color(Integer.parseInt("E1E1E1",16));

Color frameColor = new Color(Integer.parseInt("50505f",16));
Color externalTimeColor = new Color(Integer.parseInt("40b040",16));
Color internalTimeColor = new Color(Integer.parseInt("f0f000",16));
Color timeLiningColor = new Color(Integer.parseInt("50505f",16));
Color tmpTimeColor = new Color(Integer.parseInt("80f080",16));
Color nowColor = new Color(Integer.parseInt("FF0000",16));

int[] sizes = {200, 300};

BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

Graphics g = buffer.createGraphics();
// set background
g.setColor(background);
g.fillRect(0,0,width,height);

int pxPerHr = width/(HOURS_TO - HOURS_FROM + 1);

//get current time
Calendar calendar = Calendar.getInstance();
double hourNow = pxPerHr/2 + (calendar.get(Calendar.HOUR_OF_DAY) + ((double)calendar.get(Calendar.MINUTE))/60 - HOURS_FROM) * pxPerHr;

if (entries.size()> 0 ) {
	// we have some parameters - let's start drawing the time bars		
	
	int[] startTimes = new int[entries.size()]; // = {907, 1000, 1120, 1157, 1240, 1430};
	int[] endTimes = new int[entries.size()]; // = {930, 1120, 1157, 12, 1430, 1730};
	boolean[] isInternals = new boolean[entries.size()];
	
	for (int i = 0; i < entries.size(); i++) {
		startTimes[i] = Integer.parseInt(entries.get(i).getStartTimeString());	
		endTimes[i] = Integer.parseInt(entries.get(i).getEndTimeString());	
		isInternals[i] = entries.get(i).getIsInternal();	
		
	} // end for
	for (int i = 0; i < startTimes.length; i++) {
	    int startHour = (startTimes[i]/100);
	    int startMins = startTimes[i] - startHour * 100;
	    double startDecHours = startHour + startMins/60.0;
	
	    int stopHour = (endTimes[i]/100);
	    int stopMins = endTimes[i] - stopHour * 100;
	    double stopDecHours = stopHour + stopMins/60.0;
	
	    if (isInternals[i] == true) {
		    g.setColor(internalTimeColor);
	    } else {
		    g.setColor(externalTimeColor);
	    }
	    g.fillRect( (int) (pxPerHr/2 + ((startDecHours-HOURS_FROM) * pxPerHr)),
	            (height*10)/19,
	            (int) ((stopDecHours-startDecHours) * pxPerHr),
	            (height*4)/17);   
	
	    g.setColor(timeLiningColor);
	    g.drawRect( (int) (pxPerHr/2 + ((startDecHours-HOURS_FROM) * pxPerHr)),
	            (height*10)/19,
	            (int) ((stopDecHours-startDecHours) * pxPerHr),
	            (height*4)/17);       
	} // end for
	/* draw not posted time if any */
	try {
		String tmpTime = request.getParameter("tmpTime");
		if (tmpTime.length() > 0) {
		    g.setColor(tmpTimeColor);
			
		    int startHour = (Integer.parseInt(tmpTime)/100);
		    int startMins = Integer.parseInt(tmpTime) - startHour * 100;
		    double startDecHours = startHour + startMins/60.0;
		
			g.fillRect( (int) (pxPerHr/2 + ((startDecHours-HOURS_FROM) * pxPerHr)),
		            (height*10)/19,
		            (int) ((hourNow) - (pxPerHr/2 + ((startDecHours-HOURS_FROM) * pxPerHr))) + 1,
		            (height*4)/17);   	
					
		} // end if 
	} // end try
	catch (NullPointerException e) {
		// do nothing
	} // end catch
	
} // end if

	
// draw the axis and labels lastly to put it on top layer
g.setColor(frameColor);
String s;
for (int hours = 0; hours < (HOURS_TO - HOURS_FROM + 1); hours++) {
    g.fillRect(pxPerHr/2 + hours * pxPerHr, (height*7)/10, 2, 10); // hour marks

    s = new Integer( HOURS_FROM + hours).toString();

    g.drawChars(s.toCharArray(), 0,
            s.length(),
            pxPerHr/2 + hours * pxPerHr - (pxPerHr * s.length() / 20),
            height - (height/14)); // hour labels
} // end for 
g.fillRect(
        pxPerHr/2,
        (height * 3)/4,
        (HOURS_TO - HOURS_FROM) * pxPerHr, 
        2);    // axis

// draw now indicator
g.setColor(nowColor);
g.fillRect((int) hourNow,
		(height*10)/19,
		2,
        (height*4)/17);   

response.setContentType("image/png");
OutputStream os = response.getOutputStream();
ImageIO.write(buffer, "png", os);
os.close();
%>