package visualisation;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import anomalies.Anomalie;


/**
 * (non utilisee pour le moment)
 * CourbeAnomalie permet de tracer la courbe d'une anomalie
 * @author riviere
 *
 */
public class CourbeAnomalie extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ChartPanel chartPanel;
	XYPlot plot;

	public CourbeAnomalie(Anomalie anomalie){

		XYSeries series = new XYSeries("");

		double coef=0.0;
		double firstPoint= anomalie.getInitValue();
		double decrease= anomalie.getDecreasingValue();
		if(decrease!=0.0){
			coef=firstPoint/decrease;
		
					
		}
		else{
			int crease=(int)anomalie.getIncreasingValue();	
			coef=(10-firstPoint)/crease;
		}


		series.add(0.0, firstPoint);
		series.add(coef, 0.0);

		XYSeriesCollection seriesCollection = new XYSeriesCollection();
		seriesCollection.addSeries(series);

		JFreeChart chart = ChartFactory.createXYLineChart("Anomaly type "+anomalie.getType(),
				"Step of simulation", "Values", seriesCollection,
				PlotOrientation.VERTICAL, true, true, false);


		plot = (XYPlot) chart.getPlot();

		ValueAxis axis = plot.getDomainAxis();
		axis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		axis.setAutoRange(false);
		plot.setDomainAxis(axis);


		chartPanel = new ChartPanel(chart, true);
		chart.removeLegend();

		this.add(chartPanel);
		this.setVisible(true);
	}


}

