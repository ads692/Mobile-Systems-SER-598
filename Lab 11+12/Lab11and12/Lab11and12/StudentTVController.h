//
//  StudentTableViewController.h
//  Lab11-Sqlite
//
//  Created by Aditya Narasimhamurthy on 4/18/15.
//  Copyright (c) 2015 Aditya Narasimhamurthy. All rights reserved.
//
#import <UIKit/UIKit.h>
#import <sqlite3.h>
#import "CourseTVController.h"

@interface StudentTVController : UITableViewController

@property (strong, nonatomic) CourseTVController * parent;
@property (strong, nonatomic) NSString * selectedCourse;
@property (strong, nonatomic) CourseDBManager * crsDB;

@end
