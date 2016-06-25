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
 * @version March 28, 2015
 */

#import "WaypointTableViewController.h"
#import "DetailsViewController.h"
#import "AddWaypointController.h"

@interface WaypointTableViewController ()

@end

@implementation WaypointTableViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.waypointLibrary = [[WaypointLibrary alloc] init];
    
    Waypoint * ny = [[Waypoint alloc] initWithLat:54.4372 lon:-54.8659 name:@"New-York" address:@"232 E Lion Street"  category:@"Hiking"];
    Waypoint * asup = [[Waypoint alloc] initWithLat:39.3061 lon:-109.2311 name:@"ASUP" address:@"432 S Chola Street"  category:@"Travel"];
    Waypoint * asub = [[Waypoint alloc] initWithLat:35.4498 lon:-104.9651 name:@"ASUB" address:@"543 N Maverick Street"  category:@"School"];
    Waypoint * paris = [[Waypoint alloc] initWithLat:28.9876 lon:32.7642 name:@"Paris" address:@"676 W Jones Street"  category:@"Travel"];
    Waypoint * moscow = [[Waypoint alloc] initWithLat:91.4435 lon:75.3003 name:@"Moscow" address:@"212 E Doe Street"  category:@"Travel"];
    
    [self.waypointLibrary addWaypoint:ny];
    [self.waypointLibrary addWaypoint:asup];
    [self.waypointLibrary addWaypoint:asub];
    [self.waypointLibrary addWaypoint:paris];
    [self.waypointLibrary addWaypoint:moscow];

    
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    // Return the number of sections.
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    // Return the number of rows in the section.

    return self.waypointLibrary.getNames.count;
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    NSString *reuseIdentifier = @"cell";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:reuseIdentifier forIndexPath:indexPath];
    if(cell == nil){
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:reuseIdentifier];
    }

    cell.textLabel.text = self.waypointLibrary.getNames[indexPath.row];
    cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
    return cell;

}


/*
// Override to support conditional editing of the table view.
- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath {
    // Return NO if you do not want the specified item to be editable.
    return YES;
}
*/

/*
// Override to support editing the table view.
- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath {
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        // Delete the row from the data source
        [tableView deleteRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationFade];
    } else if (editingStyle == UITableViewCellEditingStyleInsert) {
        // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
    }   
}
*/

/*
// Override to support rearranging the table view.
- (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath {
}
*/

/*
// Override to support conditional rearranging of the table view.
- (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath {
    // Return NO if you do not want the item to be re-orderable.
    return YES;
}
*/

- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    [self.tableView reloadData]; // to reload selected cell
}

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    if([segue.identifier isEqualToString:@"details"]){
        DetailsViewController *controller = segue.destinationViewController;
        controller.waypointLibrary = self.waypointLibrary;
        controller.selectedWaypoint = self.waypointLibrary.getNames[[self.tableView indexPathForSelectedRow].row ];
        
    }else if([segue.identifier isEqualToString:@"add"]){
        AddWaypointController *controller = segue.destinationViewController;
        controller.waypointLibrary = self.waypointLibrary;
    }
    
}


@end
