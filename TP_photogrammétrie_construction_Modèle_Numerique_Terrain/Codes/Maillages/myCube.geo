// Gmsh project created on Fri Aug 16 11:18:38 2013
lx = 1.0;
ly = 1.0;
lz = 1.0;

N = 5;
x0 = 0*lx / N; x1 = lx / N; x2 = 2*lx / N; x3 = 3*lx / N; x4 = 4*lx / N; x5 = 5*lx / N;

Point(1) = {0, 0, 0, 1.0};
Point(2) = {lx, 0, 0, 1.0};
Point(3) = {lx, ly, 0, 1.0};
Point(4) = {0, ly, 0, 1.0};


Point(5) = {0, 0, lz, 1.0};
Point(6) = {lx, 0, lz, 1.0};
Point(7) = {lx, ly, lz, 1.0};
Point(8) = {0, ly, lz, 1.0};




Line(1) = {1, 2};
Line(2) = {2, 3};
Line(3) = {3, 4};
Line(4) = {4, 1};
Line(5) = {5, 6};
Line(6) = {6, 7};
Line(7) = {7, 8};
Line(8) = {8, 5};

Line(9) = {1, 5};
Line(10) = {2, 6};
Line(11) = {3, 7};
Line(12) = {4, 8};

Line Loop(13) = {1, 2, 3, 4};
Line Loop(14) = {5, 6, 7, 8};

Line Loop(15) = {1, 10, -5, -9};
Line Loop(16) = {2, 11, -6, -10};
Line Loop(17) = {3, 12, -7, -11};
Line Loop(18) = {4, 9, -8, -12};

Plane Surface(19) = {13};
Plane Surface(20) = {14};
Plane Surface(21) = {15};
Plane Surface(22) = {16};
Plane Surface(23) = {17};
Plane Surface(24) = {18};
Surface Loop(25) = {19, 21, 22, 23, 24, 20};
Volume(26) = {25};
Transfinite Line {5, 6, 7, 8} = 61 Using Progression 1;//81
Transfinite Line {1, 2, 3, 4} = 11 Using Progression 1;//81
Transfinite Line {9, 10, 11, 12} = 21 Using Progression 1;
Transfinite Surface {19};
//Transfinite Surface {20};
//Transfinite Surface {21};
//Transfinite Surface {22};
//Transfinite Surface {23};
//Transfinite Surface {24};
//Transfinite Volume {26};
//Recombine Surface {21, 20, 23};
//Recombine Surface {22, 20, 24};
//Recombine Surface {20, 19};
