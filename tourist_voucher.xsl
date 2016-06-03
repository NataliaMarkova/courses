<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl= "http://www.w3.org/1999/XSL/Transform" version="1.0">
	
	<xsl:template match="/"> 
		<html>
  		<body>
		<h1 align="center">Tourist vouchers</h1> 
		
		<xsl:for-each select="vouchers/voucher">
    			<h2 aligh="left">Voucher N <xsl:value-of select="@id" /></h2>
			<div>
			<b>Type: </b><xsl:value-of select="Type" /><br/>	
			<b>Country: </b><xsl:value-of select="Country" /><br/>	
			<xsl:value-of select ="Duration"/>
			<b>Duration (day/nights): </b> <xsl:value-of select="Duration/@days"/>/<xsl:value-of select="Duration/@nights"/><br/>	
			<b>Transport: </b><xsl:value-of select="Transport" /><br/>
			<b>Hotel: </b><xsl:value-of select="Hotel/@name" /><xsl:value-of select="Hotel/@type" /><br/>
			<b>Room: </b><xsl:value-of select="Room/@type" />, <xsl:value-of select="Room/@nutrition" />
			<xsl:variable name="set" select="Room/Facilities/Facility" />
        		<xsl:variable name="count" select="count($set)" />
			(<xsl:for-each select="$set">
				<xsl:value-of select="." /><xsl:if test="position() &lt; $count">, </xsl:if>
			</xsl:for-each>)<br/>
			</div>
			<b>Total price: </b><xsl:value-of select="Cost/@total" />
			(intcludes: 
			<xsl:variable name="set" select="Cost/Includes" />
        		<xsl:variable name="count" select="count($set)" />
			<xsl:for-each select="$set">
				<xsl:value-of select="." /><xsl:if test="position() &lt; $count">, </xsl:if>
			</xsl:for-each>)<br/>
    		</xsl:for-each>

  		</body>
		</html>
	</xsl:template>

</xsl:stylesheet>