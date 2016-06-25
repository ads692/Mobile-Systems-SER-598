//
//  CourseDetails.h
//  Lab11-Sqlite
//
//  Created by Aditya Narasimhamurthy on 4/18/15.
//  Copyright (c) 2015 Aditya Narasimhamurthy. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <WebKit/WebKit.h>
#import "CourseDBManager.h"
@interface CourseDetails : UIViewController

@property (strong, nonatomic) CourseDBManager * courseDB;
@end
