```markdown
### 5.2.1 Test 1: User Registration & Login

**Test 1.1: Registration Form Validation (Negative)**
| Test Case ID | Description | Pre-conditions | Test Steps | Expected Result | Actual Result | Status |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| TC-1.1 | Verify system rejects invalid inputs during registration. | User is on the Registration page. | 1. Enter email without '@'.<br>2. Enter password 'abc'.<br>3. Enter alphabets in phone.<br>4. Click 'Register'. | Prevents submission; displays error tooltips for email, password, and phone. | Matches Expected Result | Pass |

**Testing Process:**
1. Navigate to Registration page.
2. Fill form with incorrect data (`invalidemail.com`, `abc`, `+123ABC`).
3. Click submit.
4. Observe client-side / server-side error spans.
*Evidence:* `[Insert Screenshot: Registration form showing validation errors]`

**Test 1.2: Successful User Registration**
| Test Case ID | Description | Pre-conditions | Test Steps | Expected Result | Actual Result | Status |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| TC-1.2 | Verify a user can successfully register with valid details. | Email/phone are unique. | 1. Select 'Customer'.<br>2. Enter valid Name, Email, Phone, Password.<br>3. Click 'Register'. | User created in DB; redirects to Login page with success parameter `?registered=1`. | Matches Expected Result | Pass |

**Testing Process:**
1. Select 'Customer' radio button.
2. Input valid data (`John Doe`, `john@test.com`, `+1234567890`, `Test@1234`).
3. Click 'Create account'.
4. Verify redirection to login.
*Evidence:* `[Insert Screenshot: Login page showing "Account created successfully"]`

**Test 1.3: Duplicate Email Registration (Negative)**
| Test Case ID | Description | Pre-conditions | Test Steps | Expected Result | Actual Result | Status |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| TC-1.3 | Prevent registration with an already existing email. | Account with `john@test.com` already exists. | 1. Enter `john@test.com` and valid remaining data.<br>2. Click 'Register'. | Form rejected. Displays "User is already registered with this email." | Matches Expected Result | Pass |

**Testing Process:**
1. Enter an email address known to exist in the database.
2. Fill remaining fields correctly.
3. Submit form.
4. Observe duplicate email error label.
*Evidence:* `[Insert Screenshot: Registration form showing duplicate email error]`

**Test 1.4: Successful User Login**
| Test Case ID | Description | Pre-conditions | Test Steps | Expected Result | Actual Result | Status |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| TC-1.4 | Verify user can log in with valid credentials. | Registered account exists. | 1. Go to Login.<br>2. Enter valid Email & Password.<br>3. Click 'Login'. | Authentication succeeds. User redirected to their specific dashboard. | Matches Expected Result | Pass |

**Testing Process:**
1. Navigate to Login page.
2. Input registered credentials.
3. Submit form.
4. Observe successful transition to Dashboard.
*Evidence:* `[Insert Screenshot: Customer/Provider Dashboard post-login]`

---

### 5.2.2 Test 2: Service Search and Browsing

**Test 2.1: Category Filtering (Customer)**
| Test Case ID | Description | Pre-conditions | Test Steps | Expected Result | Actual Result | Status |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| TC-2.1 | Customers can filter services by category. | Categories exist in DB. | 1. Log in.<br>2. Go to Catalog.<br>3. Click 'Plumbing' filter. | Only plumbing providers are listed. | Matches Expected Result | Pass |

**Testing Process:**
1. As Customer, navigate to Search Catalog.
2. Apply a specific category filter.
3. Verify grid displays matching providers.
*Evidence:* `[Insert Screenshot: Valid filtered provider results]`

**Test 2.2: Provider Details View**
| Test Case ID | Description | Pre-conditions | Test Steps | Expected Result | Actual Result | Status |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| TC-2.2 | Customer can view full profile of a provider. | Provider has bio/experience. | 1. Click a Provider card.<br>2. View profile details. | Shows Name, Bio, Experience, Rating, and offered services. | Matches Expected Result | Pass |

**Testing Process:**
1. Click a Provider's "View Profile" link.
2. Cross-check UI details against database.
*Evidence:* `[Insert Screenshot: Provider detail page]`

**Test 2.3: Search Provider by Keywords**
| Test Case ID | Description | Pre-conditions | Test Steps | Expected Result | Actual Result | Status |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| TC-2.3 | Customers can search providers via search bar. | Providers exist. | 1. Type "Fix" in search bar.<br>2. Hit Enter/Search. | Returns providers whose name or service matches "Fix". | Matches Expected Result | Pass |

**Testing Process:**
1. Type a partial provider name matching existing DB records.
2. Click search.
3. Validate results.
*Evidence:* `[Insert Screenshot: Typed keyword search results]`

**Test 2.4: Empty Search Results Handling**
| Test Case ID | Description | Pre-conditions | Test Steps | Expected Result | Actual Result | Status |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| TC-2.4 | System smoothly handles 0 matches. | No provider matches query. | 1. Type "Zyxwvut".<br>2. Search. | Displays user-friendly "No providers found" message. | Matches Expected Result | Pass |

**Testing Process:**
1. Search for gibberish text.
2. Verify UI does not crash or show empty white space.
*Evidence:* `[Insert Screenshot: "No providers found" feedback state]`

---

### 5.2.3 Test 3: Booking a Service

**Test 3.1: Booking with Invalid Date (Negative)**
| Test Case ID | Description | Pre-conditions | Test Steps | Expected Result | Actual Result | Status |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| TC-3.1 | Prevent booking in the past. | On booking screen. | 1. Select date from yesterday.<br>2. Confirm. | Blocks booking. Shows "Invalid Date/Time" error. | Matches Expected Result | Pass |

**Testing Process:**
1. Select a past date in DatePicker.
2. Submit form.
*Evidence:* `[Insert Screenshot: Past date validation error]`

**Test 3.2: Successful Booking Process**
| Test Case ID | Description | Pre-conditions | Test Steps | Expected Result | Actual Result | Status |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| TC-3.2 | Book a valid time slot. | Provider is available. | 1. Select future datetime.<br>2. Enter Address.<br>3. Confirm. | Booking created ('Pending' status). Redirects to My Bookings. | Matches Expected Result | Pass |

**Testing Process:**
1. Fill booking form with valid date/time and address.
2. Process booking.
*Evidence:* `[Insert Screenshot: My Bookings page with new Pending booking]`

**Test 3.3: Booking with Missing Address (Negative)**
| Test Case ID | Description | Pre-conditions | Test Steps | Expected Result | Actual Result | Status |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| TC-3.3 | Enforce mandatory service address. | On booking screen. | 1. Select valid date.<br>2. Leave Address blank.<br>3. Confirm. | Booking prevented. "Address is required" validation shown. | Matches Expected Result | Pass |

**Testing Process:**
1. Leave the location/address field entirely blank.
2. Attempt submission.
*Evidence:* `[Insert Screenshot: Missing address form validation]`

**Test 3.4: Booking Unavailability Conflict (Negative)**
| Test Case ID | Description | Pre-conditions | Test Steps | Expected Result | Actual Result | Status |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| TC-3.4 | Prevent booking outside provider's registered hours. | Provider works 9AM-5PM. | 1. Select 8:00 PM time slot.<br>2. Confirm. | Booking rejected. Shows "Provider unavailable at this time." | Matches Expected Result | Pass |

**Testing Process:**
1. Select a time slot the provider explicitly didn't mark as available.
2. Confirm booking.
*Evidence:* `[Insert Screenshot: Schedule conflict error message]`

---

### 5.2.4 Test 4: Ratings and Feedback

**Test 4.1: Rating an Incomplete Booking (Negative)**
| Test Case ID | Description | Pre-conditions | Test Steps | Expected Result | Actual Result | Status |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| TC-4.1 | Prevent rating prior to job completion. | Booking is 'Pending'. | 1. Open Pending booking.<br>2. Find rate button. | Rating UI is hidden/disabled. | Matches Expected Result | Pass |

**Testing Process:**
1. View a Pending/In Progress booking.
2. Verify rating section is not accessible yet.
*Evidence:* `[Insert Screenshot: No rate option on Pending booking]`

**Test 4.2: Successful Rating Submission**
| Test Case ID | Description | Pre-conditions | Test Steps | Expected Result | Actual Result | Status |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| TC-4.2 | Allow rating on completed jobs. | Booking is 'Completed'. | 1. View completed job.<br>2. Give 5 stars & text.<br>3. Submit. | Rating saved, provider average score recalculates. | Matches Expected Result | Pass |

**Testing Process:**
1. Find a Completed booking.
2. Submit a valid review.
3. Check provider profile.
*Evidence:* `[Insert Screenshot: Submitted review and updated score]`

**Test 4.3: Missing Star Rating (Negative)**
| Test Case ID | Description | Pre-conditions | Test Steps | Expected Result | Actual Result | Status |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| TC-4.3 | Require integer star value. | Booking 'Completed'. | 1. Type text comment.<br>2. Do NOT select stars.<br>3. Submit. | Form rejects submission. "Please select a star rating." | Matches Expected Result | Pass |

**Testing Process:**
1. Open Rating module.
2. Leave stars empty, add text, submit.
*Evidence:* `[Insert Screenshot: Validation error on missing stars]`

**Test 4.4: Preventing Duplicate Reviews (Negative)**
| Test Case ID | Description | Pre-conditions | Test Steps | Expected Result | Actual Result | Status |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| TC-4.4 | Prevent rating the same booking twice. | Booking already rated. | 1. Open rated booking.<br>2. Try rating again. | Input hidden. Message: "You have already reviewed this service." | Matches Expected Result | Pass |

**Testing Process:**
1. Return to the booking reviewed in TC-4.2.
2. Confirm the UI prevents a second rating entry.
*Evidence:* `[Insert Screenshot: Duplicate review prevention UI message]`

---

### 5.2.5 Test 5: Profile & Service Management (Provider)

**Test 5.1: Profile Update (Negative Exp)**
| Test Case ID | Description | Pre-conditions | Test Steps | Expected Result | Actual Result | Status |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| TC-5.1 | Prevent negative values in experience. | Provider logged in. | 1. Go to Profile.<br>2. Enter '-2' experience.<br>3. Save. | Error: "Experience cannot be negative". | Matches Expected Result | Pass |

**Testing Process:**
1. Input invalid `-2` in experience field.
2. Attempt profile update.
*Evidence:* `[Insert Screenshot: Validation error for negative number]`

**Test 5.2: Adding a New Service**
| Test Case ID | Description | Pre-conditions | Test Steps | Expected Result | Actual Result | Status |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| TC-5.2 | Link service to provider profile. | Active Profile. | 1. Services tab.<br>2. Add 'Carpentry' + $50/hr.<br>3. Save. | Service saved and displayed on Customer end. | Matches Expected Result | Pass |

**Testing Process:**
1. Select service from select menu, imput rate, Save.
*Evidence:* `[Insert Screenshot: Provider Services list showing the new service]`

**Test 5.3: Removing an Existing Service**
| Test Case ID | Description | Pre-conditions | Test Steps | Expected Result | Actual Result | Status |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| TC-5.3 | Unlink service from provider. | Provider offers 'Carpentry'. | 1. Services tab.<br>2. Click 'Remove' on Carpentry.<br>3. Confirm. | Service deleted. Provider no longer shows up under Carpentry. | Matches Expected Result | Pass |

**Testing Process:**
1. Click delete/trash icon on an active service.
2. Verify it drops from list and customer search.
*Evidence:* `[Insert Screenshot: Empty/removed service table]`

**Test 5.4: Updating Service Rate**
| Test Case ID | Description | Pre-conditions | Test Steps | Expected Result | Actual Result | Status |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| TC-5.4 | Modify existing pricing. | Provider offers a service. | 1. Edit Service.<br>2. Change price from 50 to 60.<br>3. Save. | Price updates in DB and storefront instantly. | Matches Expected Result | Pass |

**Testing Process:**
1. Alter the numeric rate of an existing service.
2. Save and check Customer dashboard for the new rate.
*Evidence:* `[Insert Screenshot: Updated price rate label in UI]`

---

### 5.2.6 Test 6: Availability Scheduling (Provider)

**Test 6.1: Overlapping Availability (Negative)**
| Test Case ID | Description | Pre-conditions | Test Steps | Expected Result | Actual Result | Status |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| TC-6.1 | Prevent overlapping shifts. | Provider logged in. | 1. Set Mon 09:00-12:00.<br>2. Add Mon 10:00-14:00. | Rejects save: "Time slots cannot overlap". | Matches Expected Result | Pass |

**Testing Process:**
1. Input overlapping datetimes in calendar manager.
2. Save.
*Evidence:* `[Insert Screenshot: Overlapping schedule error]`

**Test 6.2: Setting Weekly Availability**
| Test Case ID | Description | Pre-conditions | Test Steps | Expected Result | Actual Result | Status |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| TC-6.2 | Add valid working hours. | Provider logged in. | 1. Add Tues 10:00-16:00.<br>2. Save. | Range added to DB. Customers can book this window. | Matches Expected Result | Pass |

**Testing Process:**
1. Map a valid time block for a future open day.
2. Save.
*Evidence:* `[Insert Screenshot: Active schedule block on Provider calendar]`

**Test 6.3: Clearing Availability Slots**
| Test Case ID | Description | Pre-conditions | Test Steps | Expected Result | Actual Result | Status |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| TC-6.3 | Delete a previously set time slot. | Has Mon 09:00-12:00 slot. | 1. Select the Mon shift.<br>2. Click Delete/Clear. | Slot removed. Customers can no longer book it. | Matches Expected Result | Pass |

**Testing Process:**
1. Remove a block from the schedule visually.
2. Verify customer booking module blocks that date/time now.
*Evidence:* `[Insert Screenshot: Provider schedule showing the removed time block]`

**Test 6.4: Setting Availability in Past (Negative)**
| Test Case ID | Description | Pre-conditions | Test Steps | Expected Result | Actual Result | Status |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| TC-6.4 | Prevent scheduling in history. | Provider logged in. | 1. Add shift for 2 days ago.<br>2. Save. | Error: "Cannot set availability for past dates." | Matches Expected Result | Pass |

**Testing Process:**
1. Exploit calendar to select a previous month/day.
2. Attempt logic save.
*Evidence:* `[Insert Screenshot: Error regarding past schedule logic]`

---

### 5.2.7 Test 7: Booking Status Management (Provider)

**Test 7.1: Invalid Status Jump (Negative)**
| Test Case ID | Description | Pre-conditions | Test Steps | Expected Result | Actual Result | Status |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| TC-7.1 | Prevent straight jump to Completed. | Booking is 'Pending'. | 1. Open booking.<br>2. Try selecting 'Completed'. | Must transition to 'Accepted' first. 'Completed' is disabled. | Matches Expected Result | Pass |

**Testing Process:**
1. Check dropdown commands on a new request.
2. Ensure illogical life-cycles (Pending -> Finish) are restricted.
*Evidence:* `[Insert Screenshot: Disabled 'Complete' button for Pending jobs]`

**Test 7.2: Accepting a Booking**
| Test Case ID | Description | Pre-conditions | Test Steps | Expected Result | Actual Result | Status |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| TC-7.2 | Provider accepts request. | 'Pending' request. | 1. Open pending.<br>2. Click 'Accept'. | Status changes to 'Accepted' for both users. | Matches Expected Result | Pass |

**Testing Process:**
1. Accept the job in UI.
2. Verify Status string changes.
*Evidence:* `[Insert Screenshot: Booking row changed to Accepted]`

**Test 7.3: Rejecting a Booking**
| Test Case ID | Description | Pre-conditions | Test Steps | Expected Result | Actual Result | Status |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| TC-7.3 | Provider rejects request. | 'Pending' request. | 1. Open pending.<br>2. Click 'Reject'. | Status becomes 'Declined/Rejected'. Slot frees up. | Matches Expected Result | Pass |

**Testing Process:**
1. Reject a new request.
2. Ensure Customer sees rejection layout.
*Evidence:* `[Insert Screenshot: Booking marked as Rejected]`

**Test 7.4: Completing an In-Progress Job**
| Test Case ID | Description | Pre-conditions | Test Steps | Expected Result | Actual Result | Status |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| TC-7.4 | Lifecycle finish. | Booking is 'In Progress'. | 1. Open job.<br>2. Click 'Mark Completed'. | Status updates. Rating module opens for customer. | Matches Expected Result | Pass |

**Testing Process:**
1. Progress a job to IN PROGRESS, then to COMPLETED.
2. Validate final transition unlocks review system.
*Evidence:* `[Insert Screenshot: Fully completed booking status card]`

---

### 5.2.8 Test 8: Provider Verification (Admin)

**Test 8.1: Viewing Pending Providers**
| Test Case ID | Description | Pre-conditions | Test Steps | Expected Result | Actual Result | Status |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| TC-8.1 | Admin view queue. | Provider just registered. | 1. Admin login.<br>2. Go to 'Verification'. | Provider listed as 'Pending'. | Matches Expected Result | Pass |

**Testing Process:**
1. Register new Provider.
2. Check Admin table for presence.
*Evidence:* `[Insert Screenshot: Verification Queue table]`

**Test 8.2: Approving Provider Status**
| Test Case ID | Description | Pre-conditions | Test Steps | Expected Result | Actual Result | Status |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| TC-8.2 | Admin approves access. | Provider 'Pending'. | 1. Click 'Approve'. | Status updates to 'Approved'. Provider is widely visible. | Matches Expected Result | Pass |

**Testing Process:**
1. Click Approve on the new user.
2. Confirm via Customer search that they appear.
*Evidence:* `[Insert Screenshot: Approved success message]`

**Test 8.3: Rejecting a Provider**
| Test Case ID | Description | Pre-conditions | Test Steps | Expected Result | Actual Result | Status |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| TC-8.3 | Admin denies access. | Provider 'Pending'. | 1. Click 'Reject'. | Status becomes 'Rejected'. User cannot be booked. | Matches Expected Result | Pass |

**Testing Process:**
1. Click Reject.
2. Verify Provider portal warns them of banned/rejected state.
*Evidence:* `[Insert Screenshot: Provider account marked Rejected]`

**Test 8.4: Viewing Approved Providers List**
| Test Case ID | Description | Pre-conditions | Test Steps | Expected Result | Actual Result | Status |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| TC-8.4 | Filter by Approved. | Multiple users exist. | 1. Admin dashboard.<br>2. Filter by 'Approved'. | Returns only active/approved providers globally. | Matches Expected Result | Pass |

**Testing Process:**
1. Toggle the table filter manually.
2. Verify data matches active users.
*Evidence:* `[Insert Screenshot: Filtered Grid of Approved Providers]`

---

### 5.2.9 Test 9: Complaints Management

**Test 9.1: Customer Raising Complaint**
| Test Case ID | Description | Pre-conditions | Test Steps | Expected Result | Actual Result | Status |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| TC-9.1 | Submit complaint logic. | Past booking exists. | 1. Booking details.<br>2. Click 'Complaint'.<br>3. Submit. | Complaint logged with 'Open' status. | Matches Expected Result | Pass |

**Testing Process:**
1. Find old booking.
2. Submit a complaint ticket text.
*Evidence:* `[Insert Screenshot: Form submission success dialog]`

**Test 9.2: Customer Complaint Empty Text (Negative)**
| Test Case ID | Description | Pre-conditions | Test Steps | Expected Result | Actual Result | Status |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| TC-9.2 | Requires description. | Raising complaint. | 1. Leave text box blank.<br>2. Submit. | Error: "Complaint description cannot be empty." | Matches Expected Result | Pass |

**Testing Process:**
1. Submit empty complaint form.
2. View UI validation.
*Evidence:* `[Insert Screenshot: Textbox required alert]`

**Test 9.3: Admin Resolving Complaint**
| Test Case ID | Description | Pre-conditions | Test Steps | Expected Result | Actual Result | Status |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| TC-9.3 | Close a ticket. | 'Open' complaint exists. | 1. Admin Complaints list.<br>2. Click 'Resolve'. | Marked 'Resolved'. Customer notified. | Matches Expected Result | Pass |

**Testing Process:**
1. Admin resolves the open ticket.
2. Verify UI updates globally.
*Evidence:* `[Insert Screenshot: Resolved status tag]`

**Test 9.4: Customer Views Complaint Status**
| Test Case ID | Description | Pre-conditions | Test Steps | Expected Result | Actual Result | Status |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| TC-9.4 | View resolution. | Complaint was resolved. | 1. Customer login.<br>2. Go to 'My Complaints'. | Shows the complaint history marked as 'Resolved'. | Matches Expected Result | Pass |

**Testing Process:**
1. Customer accesses their Support/Complaints tab.
2. Status matches admin's action.
*Evidence:* `[Insert Screenshot: Customer-side complaint log]`

---

### 5.2.10 Test 10: Real-time Notifications

**Test 10.1: Notification on New Booking**
| Test Case ID | Description | Pre-conditions | Test Steps | Expected Result | Actual Result | Status |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| TC-10.1 | Alert on creation. | Customer books. | 1. Book time.<br>2. Provider logs in. | Unread badge icon for new request. | Matches Expected Result | Pass |

**Testing Process:**
1. Make a fresh booking.
2. Check Provider notification bell.
*Evidence:* `[Insert Screenshot: Notification dropdown for New Request]`

**Test 10.2: Notification on Status Change**
| Test Case ID | Description | Pre-conditions | Test Steps | Expected Result | Actual Result | Status |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| TC-10.2 | Alert on accept. | Provider accepts. | 1. Accept booking.<br>2. Customer logs in. | Unread badge: "Booking Accepted". | Matches Expected Result | Pass |

**Testing Process:**
1. Progress request to Accepted.
2. Check Customer bell.
*Evidence:* `[Insert Screenshot: Notification dropdown for Acceptance]`

**Test 10.3: Notification on Booking Rejection**
| Test Case ID | Description | Pre-conditions | Test Steps | Expected Result | Actual Result | Status |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| TC-10.3 | Alert on decline. | Provider rejects. | 1. Reject booking.<br>2. Customer logs in. | Unread badge: "Booking Declined. Please select another provider." | Matches Expected Result | Pass |

**Testing Process:**
1. Deny request.
2. Check Customer bell.
*Evidence:* `[Insert Screenshot: Notification dropdown for Rejection]`

**Test 10.4: Marking Notifications as Read**
| Test Case ID | Description | Pre-conditions | Test Steps | Expected Result | Actual Result | Status |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| TC-10.4 | Clearing unread states. | User has unread alert. | 1. Open Notification tray.<br>2. Click 'Mark all read'. | Unread counter / red badge disappears. | Matches Expected Result | Pass |

**Testing Process:**
1. Trigger event to click "Mark active/read".
2. Confirm UI drops the unread styling.
*Evidence:* `[Insert Screenshot: Empty / cleared notification bell]`
```