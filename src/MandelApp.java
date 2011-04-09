import javax.swing.*;
import java.awt.*;

public class MandelApp extends JPanel
{
	public final int iterations = 1000;

	final int x_sz = 500, y_sz = 500 ;

	Complex top_left = new Complex(-2, 2);
	Complex bottom_right_vec = new Complex(4, -4); /* to (2, -2) */

	public static void main(String args[])
	{
		new MandelApp();
	}

	public MandelApp()
	{
		JFrame jf = new JFrame();
		jf.setSize(x_sz,y_sz);
		jf.setContentPane(this);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setLocationRelativeTo(null);
		//jf.setLocation(1500, 50);
		jf.setVisible(true);
	}

	public void paintComponent(Graphics g)
	{
		for( int x = 0; x<x_sz; x++)
		{
			for(int y = 0; y<y_sz; y++)
			{
				/* z_{n+1} = z_{n}^{2} + c */
				boolean escaped = false;
				double x_cart = top_left.getReal()
					+ x * bottom_right_vec.getReal()
						/ x_sz;
				double y_cart = top_left.getImag()
					+ y * bottom_right_vec.getImag()
						/ y_sz;
				//System.out.printf("(%d, %d) -> (%f, %f)", x, y, x_cart, y_cart);
				Complex c = new Complex(x_cart, y_cart);
				Complex z = Complex.ZERO;
				int iter;
				for(iter = 0; iter < iterations; iter++)
				{
					z = z.squared().add(c);
					if(z.getAbsSquared() > 4.0)
					{
						escaped=true;
						break;
					}
				}
				if(escaped) {
					/* not in set */
					g.setColor(whichColor(iter, iterations));
				} else {
					/* has not escaped at this number of
					 * iterations */
					g.setColor(Color.BLACK);
				}
				/* draw point */
				g.drawLine(x,y, x,y);
			}
		}
	}

	public Color whichColor(int iter, int outOf)
	{
		switch(iter%6)
		{
			case 0: return new Color(39,38,73);	// do not need breaks (returning)
			case 1: return Color.BLUE;
			case 2: return Color.GREEN;
			case 3: return Color.YELLOW;
			case 4: return Color.ORANGE;
			case 5: return Color.RED;
		}
		return Color.BLACK; /* unreachable */
	}
}

class Complex
{
	double a; /* real-part */
	double b; /* imaginary-part */

	public static final Complex ZERO = new Complex(0,0);

	public Complex(double a, double b)
	{
		this.a = a;
		this.b = b;
	}

	public double getReal() { return a; }
	public double getImag() { return b; }

	public Complex multiply(Complex that)
	{
		double a = this.getReal();
		double b = this.getImag();
		double c = that.getReal();
		double d = that.getImag();
		return new Complex( a*c - b*d ,
				b*c + a*d);
	}

	public Complex add(Complex that)
	{
		return new Complex(
			this.getReal() + that.getReal()
				, this.getImag() + that.getImag());
	}

	public Complex squared() { return this.multiply(this); }

	public double getAbsSquared() { return a*a + b*b; }
}
