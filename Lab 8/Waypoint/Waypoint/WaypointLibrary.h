/**
 * Copyright (C) 2015 Aditya Narasimhamurthy
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author Aditya Narasimhamurthy mailto:aditya.murthy@asu.edu
 * @version March 23, 2015
 */


#import <Foundation/Foundation.h>
#import "Waypoint.h"
@interface WaypointLibrary : NSObject

//public properties of the waypoint include lat, lon, and name
@property NSMutableArray *waypoints;
@property (nonatomic) double lon;
@property (strong, nonatomic) NSString * name;

//// initialize a waypont object with values
//- (id) initWithLat: (double) lat
//               lon: (double) lon
//              name: (NSString *) name;

//This method takes a waypoint object as parameter and adds that object to the library collection.
- (void) addWaypoint: (Waypoint*)waypoint;

//This method takes a parameter which is the string name of a waypoint. The named waypoint is to be removed from the library.
- (BOOL) removeWaypointNamed: (NSString*) wpName;

//This method takes a parameter which is the string name of a waypoint. The named waypoint object is returned from this method.
- (Waypoint*) getWaypointNamed: (NSString*) wpName;

//This method returns an array which contains the names of all waypoints currently stored in the library.
- (NSArray*) getNames;

@end