<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl= "http://www.w3.org/1999/XSL/Transform" version="1.0"
		xmlns:vch="http://www.markova.kiev.ua/epam_courses/project3" >
	
	<xsl:template match="/"> 
		<html>
  			<body>
				<h1 align="center">Tourist vouchers</h1>

				<table align="center" border="1">
					<tr>
						<td align="center"><b>ID</b></td>
						<td align="center"><b>Type</b></td>
						<td align="center"><b>Country</b></td>
						<td align="center"><b>Number days/nights</b></td>
						<td align="center"><b>Transport</b></td>
						<td align="center"><b>Hotel characteristics</b></td>
						<td align="center"><b>Cost</b></td>
					</tr>
					<xsl:for-each select="vch:vouchers/vch:voucher">
						<tr>
							<td align="center" vlign="top"><xsl:value-of select="@id" /></td>
							<td align="center" vlign="top"><xsl:value-of select="vch:Type" /></td>
							<td align="center" vlign="top"><xsl:value-of select="vch:Country" /></td>
							<td align="center" vlign="top"><xsl:value-of select="vch:Duration/@days"/>/<xsl:value-of select="vch:Duration/@nights"/></td>
							<td align="center" vlign="top"><xsl:value-of select="vch:Transport" /></td>
							<td align="center" vlign="top"><xsl:value-of select="vch:Hotel/@name" /><xsl:value-of select="vch:Hotel/@type" /><br/>
								<xsl:value-of select="vch:Room/@type" />, <xsl:value-of select="vch:Room/@nutrition" /><br/>
								<xsl:variable name="set" select="vch:Room/vch:Facilities/vch:Facility" />
								<xsl:variable name="count" select="count($set)" />
								<xsl:for-each select="$set">
									<xsl:value-of select="." /><xsl:if test="position() &lt; $count">, </xsl:if>
								</xsl:for-each><br/>
							</td>
							<td align="center">&#8364;<xsl:value-of select="vch:Cost/@total" /><br/>
								<xsl:variable name="set" select="vch:Cost/vch:Includes" />
								<xsl:variable name="count" select="count($set)" />
								<xsl:for-each select="$set">
									<xsl:value-of select="." /><xsl:if test="position() &lt; $count">, </xsl:if>
								</xsl:for-each>
							</td>
						</tr>
					</xsl:for-each>
				</table>
  			</body>
		</html>
	</xsl:template>

</xsl:stylesheet>