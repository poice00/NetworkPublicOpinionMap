package com.om.opinionleader;

import java.util.List;

public class Util {
	/**
	 * 该指标通过浏览次数、发帖数、帖子回复数和粉丝数四个网络参量来进行度量
	 * N = g2N2 + g3N3 + g4N4
	 * AI = 2 * sigmoid( (g1* N1/ Max(N1) + g5 * N / Max(N)) * 6.0) −1
	 * simgoid (x) =  1/(1+ e-x)
	 * 由于当x=6 的时候该函数已经非常接近1，而且随着x 继续增大该值变化
	 * 特别小，所以通过归一把x 控制在[0,6]，因为sigmoid 函数的值域为[0.5,1)，
	 * 2*simoid(x)-1 的值域为[0,1)
	 * 计算影响力
	 * g1取0.05，g2取0.25，g3取0.3，g4取0.4
	 * @param N1 浏览次数 N1
	 * @param N2 作者发表文章数 N2
	 * @param N2 文章被回复数 N3
	 * @param N4 粉丝数 N4
	 * @param g1 浏览次数权值
	 * @param g2 作者发表文章数权值
	 * @param g3 文章被回复数权值
	 * @param g4 粉丝数权值
	 * @param g5 N权值
	 * @return 活跃度
	 */
	public static double calfluence(int N1,int N2,int N3,int N4,double g1,double g2,double g3,
				double g4,double g5,List<Double> N1List,List<Double> NList){
		double N = calfluencePre(N2, N3, N4, g2, g3, g4);
		double maxN1 = getMaxCalData(N1List) ;
		double maxN = getMaxCalData(NList) ;
		double ret = 2*sigmoid((0.25*N1/maxN1 + g5*N/maxN) * 6.0) - 1 ;
		return ret;
	}
	/**
	 * 归一化函数
	 * @param x
	 * @return
	 */
	public static double sigmoid(double x) {
		double result =1/(1+Math.pow(Math.E,-x));
		return result;
	}


	public static double calfluencePre(int N2,int N3,double N4,double g2,double g3,double g4){
		return g2*N2 + g3*N3 + g4*N4;
	}
	
	/**
	 * 计算最大的数
	 * @param dataList
	 * @return
	 */
	public static double getMaxCalData(List<Double> dataList){
		double result = dataList.get(0);
		for (double d : dataList) {
			if(d>result) {
				result = d ;
			}
		}
		return result;
	}
	/**
	 * 计算活跃度
	 * 通过该用户在网络中的发帖数和参与的回帖数来衡量
	 * N = g1*N1 + g2*N2
	 * AC = (N / Max(N))
	 * 式中，AC 为发布者活跃度，N1为发表的文章数，N2为参与回复数，权重分别为g1和g2。
	 * g1取0.6，g2取0.4
	 * @param N1 作者发表文章数
	 * @param N2 作者参与回复数
	 * @param g1 作者参与回复数权值
	 * @param g2 作者参与回复数权值
	 * @param dataList N的集合
	 * @return 活跃度
	 */
	public static double calActive(int N1,int N2,double g1,double g2,List<Double> dataList){
		double pre = calActivePre(N1, N2, g1, g2);
		double max  = getMaxCalData(dataList);
		return pre/max;
	}
	/**
	 * 
	 * @param N1 作者发表文章数
	 * @param N2 作者参与回复数
	 * @param g1 作者参与回复数权值
	 * @param g2 作者参与回复数权值
	 * @return
	 */
	public static double calActivePre(int N1,int N2,double g1,double g2){
		return g1*N1 + g2*N2;
	}
	public static void main(String[] args) {
		//2*sigmoid((g1*N1/maxN1 + g5*N/maxN) * 6.0) - 1 ;
//		double re = 0.25*19434/(1.8031357E7) + 0.25*24.25/18727.0;
//		System.out.println(re);
		System.out.println(2*sigmoid((0.9) * 6.0) - 1);
	}
}
