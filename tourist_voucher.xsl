<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl= "http://www.w3.org/1999/XSL/Transform" version="1.0"
		xmlns:vch="http://www.markova.kiev.ua/epam_courses/project3" >
	
	<xsl:template match="/"> 
		<html>
  		<body>
		<h1 align="center">Tourist vouchers</h1> 
		
		<xsl:for-each select="vch:vouchers/vch:voucher">
    			<h2 aligh="left">Voucher N <xsl:value-of select="@id" /></h2>
			<div>
			<b>Type: </b><xsl:value-of select="vch:Type" /><br/>	
			<b>Country: </b><xsl:value-of select="vch:Country" /><br/>	
			<xsl:value-of select ="vch:Duration"/>
			<b>Duration (day/nights): </b> <xsl:value-of select="vch:Duration/@days"/>/<xsl:value-of select="vch:Duration/@nights"/><br/>	
			<b>Transport: </b><xsl:value-of select="vch:Transport" /><br/>
			<b>Hotel: </b><xsl:value-of select="vch:Hotel/@name" /><xsl:value-of select="vch:Hotel/@type" /><br/>
			<b>Room: </b><xsl:value-of select="vch:Room/@type" />, <xsl:value-of select="vch:Room/@nutrition" />
			<xsl:variable name="set" select="vch:Room/vch:Facilities/vch:Facility" />
        		<xsl:variable name="count" select="count($set)" />
			(<xsl:for-each select="$set">
				<xsl:value-of select="." /><xsl:if test="position() &lt; $count">, </xsl:if>
			</xsl:for-each>)<br/>
			</div>
			<b>Total price: </b><xsl:value-of select="vch:Cost/@total" />
			(intcludes: 
			<xsl:variable name="set" select="vch:Cost/vch:Includes" />
        		<xsl:variable name="count" select="count($set)" />
			<xsl:for-each select="$set">
				<xsl:value-of select="." /><xsl:if test="position() &lt; $count">, </xsl:if>
			</xsl:for-each>)<br/>
    		</xsl:for-each>

  		</body>
		</html>
	</xsl:template>

</xsl:stylesheet>