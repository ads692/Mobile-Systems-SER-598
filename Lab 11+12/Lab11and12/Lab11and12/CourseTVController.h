//
//  CourseTableViewController.h
//  Lab11-Sqlite
//
//  Created by Aditya Narasimhamurthy on 4/18/15.
//  Copyright (c) 2015 Aditya Narasimhamurthy. All rights reserved.
//
#import <UIKit/UIKit.h>
#import <sqlite3.h>
#import "CourseDBManager.h"

@interface CourseTVController : UITableViewController
@property (strong, nonatomic) NSString *dbPath;
@property (nonatomic) sqlite3 *courseDB;

@property (strong, nonatomic) IBOutlet UILabel *status;


- (NSArray *) getDataQuery;
- (void)addClicked:(id)sender;

@end
