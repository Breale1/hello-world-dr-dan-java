/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Hello World with Dr. Dan - A Complete Introduction to Programming from Java to C++ (Code and Course � Dan Grissom)
//
// Additional Lesson Resources from Dr. Dan:
//		High-Quality Video Tutorials: www.helloDrDan.com
//		Free Commented Code: https://github.com/DanGrissom/hello-world-dr-dan-java
//
// Lesson Note:
// 		This class is consumed by Lesson_04_Burrito_Client.cpp
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#ifndef BURRITO_H_
#define BURRITO_H_

using namespace std;
#include <string>

class Burrito {
	private:
		// Instance variables
		string meatType;
		string beanType;

	public:
		// Constructors
		Burrito(string meat, string beans);
		virtual ~Burrito();

		// Methods
		string getSummary();

		// Getters/Setters
		string getMeatType() { return meatType; }
		string getBeanType() { return beanType; }
		void setMeatType(string meat) { meatType = meat; }
		void setBeanType(string bean) { beanType = bean; }
};

#endif /* BURRITO_H_ */
