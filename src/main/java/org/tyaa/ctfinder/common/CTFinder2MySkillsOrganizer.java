package org.tyaa.ctfinder.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Logger;

import com.google.auth.oauth2.GoogleCredentials;
// import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.ThreadManager;
// import com.google.firebase.ThreadManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
// import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.FirebaseDatabase;
// import com.google.firebase.tasks.OnCompleteListener;
// import com.google.firebase.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CTFinder2MySkillsOrganizer {

	private static Gson gson;
	private static final Logger log = Logger.getLogger(CTFinder2MySkillsOrganizer.class.getName());
	
	static {
		gson =
    		new GsonBuilder()
	    		.setDateFormat("yyyy-MM-dd").create();
		
		ThreadManager threadManager = new ThreadManager() {
	        @Override
	        protected ExecutorService getExecutor(FirebaseApp app) {
	          return Executors.newCachedThreadPool();
	        }

	        @Override
	        protected void releaseExecutor(FirebaseApp app, ExecutorService executor) {
	          executor.shutdown();
	        }

	        @Override
	        protected ThreadFactory getThreadFactory() {
	          // use GAE background thread factory
	          return com.google.appengine.api.ThreadManager.backgroundThreadFactory();
	        }
	      };
		
		FileInputStream serviceAccount = null;
		try {
			serviceAccount = new FileInputStream(new File("WEB-INF/firebaseServiceAccount.json"));
		} catch (FileNotFoundException ex) {
			// TODO Auto-generated catch block
			ObjectifyQueryLauncher.printException(ex, null, gson);
		}

		FirebaseOptions options = null;
		try {
			options = new FirebaseOptions.Builder()
			  .setCredentials(GoogleCredentials.fromStream(serviceAccount))
			// .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
			  .setDatabaseUrl("https://my-skills-organizer.firebaseio.com/")
			  .setThreadManager(threadManager)
			  .build();
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			ObjectifyQueryLauncher.printException(ex, null, gson);
		}

		FirebaseApp fa = FirebaseApp.initializeApp(options);
		log.info("step 1:  " + fa);
	}
	
	public static void createNode(String email, String title, String description) {
		
		// log.info("step 2:  " + email + " " + title + " " + description);
		UserRecord userRecord = null;
		
		
		
		//Task<UserRecord> userRecordTask = null;
		try {
			//userRecordTask =
					//FirebaseAuth.getInstance().getUserByEmail(email);
			
			userRecord = FirebaseAuth.getInstance().getUserByEmail(email);
			
			log.info("step 3:  " + userRecord);
			if (userRecord != null) {
				String uId = userRecord.getUid();
				log.info("step 4:  " + uId);
				final FirebaseDatabase database = FirebaseDatabase.getInstance();
				log.info("step 5:  " + database);
				DatabaseReference ref = database.getReference("nodes");
				log.info("step 6:  " + ref);
				MySkillsOrganizerNode node =
						new MySkillsOrganizerNode(uId, title, "2", description, "1", false, 0, 0, 50);
				

				DatabaseReference childRef = ref.push();
				
				ref.addChildEventListener(new ChildEventListener() {
					
					@Override
					public void onChildRemoved(DataSnapshot snapshot) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
						
						for (DataSnapshot child : snapshot.getChildren()) {
					          if (child.getKey().equals(childRef.getKey())) {
					        	  System.out.println("step 8:  " + child.getValue());
					          }
					        }
					}
					
					@Override
					public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
						System.out.println("step 8-0:  " + snapshot.getValue());
						for (DataSnapshot child : snapshot.getChildren()) {
					          if (child.getKey().equals(childRef.getKey())) {
					        	  System.out.println("step 8:  " + child.getValue());
					        	  // Status.getInstance().notifyUpdate();
					          }
					        }
						
					}
					
					@Override
					public void onCancelled(DatabaseError error) {
						System.out.println("step 8:  " + error.getCode());
						
					}
				});
				
				log.info("step 7:  " + childRef.getKey());
				
				// childRef.setValueAsync(node);
				childRef.setValueAsync(node);
				
				System.out.println("step 9:  " + "done");
			} else {
				ObjectifyQueryLauncher.printException(new Exception("userRecord == null"), null, gson);
			}
			
			/*userRecordTask.addOnCompleteListener(new OnCompleteListener<UserRecord>() {
				
				@Override
				public void onComplete(Task<UserRecord> task) {
					
					UserRecord userRecord = task.getResult();
					log.info("step 3:  " + userRecord);
					if (userRecord != null) {
						String uId = userRecord.getUid();
						log.info("step 4:  " + uId);
						final FirebaseDatabase database = FirebaseDatabase.getInstance();
						log.info("step 5:  " + database);
						DatabaseReference ref = database.getReference("nodes");
						log.info("step 6:  " + ref);
						MySkillsOrganizerNode node =
								new MySkillsOrganizerNode(uId, title, "2", description, "1", false, 0, 0, 50);
						

						DatabaseReference childRef = ref.push();
						
						ref.addChildEventListener(new ChildEventListener() {
							
							@Override
							public void onChildRemoved(DataSnapshot snapshot) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
								
								for (DataSnapshot child : snapshot.getChildren()) {
							          if (child.getKey().equals(childRef.getKey())) {
							        	  System.out.println("step 8:  " + child.getValue());
							          }
							        }
							}
							
							@Override
							public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
								System.out.println("step 8-0:  " + snapshot.getValue());
								for (DataSnapshot child : snapshot.getChildren()) {
							          if (child.getKey().equals(childRef.getKey())) {
							        	  System.out.println("step 8:  " + child.getValue());
							        	  // Status.getInstance().notifyUpdate();
							          }
							        }
								
							}
							
							@Override
							public void onCancelled(DatabaseError error) {
								System.out.println("step 8:  " + error.getCode());
								
							}
						});
						
						log.info("step 7:  " + childRef.getKey());
						
						// childRef.setValueAsync(node);
						childRef.setValue(node);
						
						System.out.println("step 9:  " + "done");
					} else {
						ObjectifyQueryLauncher.printException(new Exception("userRecord == null"), null, gson);
					}
				}
			});*/
		} catch (Exception ex) {
			ObjectifyQueryLauncher.printException(ex, null, gson);
		}
		
	}
}
