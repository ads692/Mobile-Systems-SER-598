//
//  SelectStudent.h
//  Lab11-Sqlite
//
//  Created by Aditya Narasimhamurthy on 4/18/15.
//  Copyright (c) 2015 Aditya Narasimhamurthy. All rights reserved.
//
#import <Foundation/Foundation.h>
#import <WebKit/WebKit.h>
#import "CourseDBManager.h"

@interface SelectStudent : UIViewController
@property (strong,nonatomic) NSMutableArray * studentList;
@property (strong, nonatomic) NSString * selectedCourse;
@property (strong, nonatomic) CourseDBManager * crsDB;
@end
