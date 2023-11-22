// import java.util.ArrayList;
// import java.util.List;

//     // Composite Pattern
    
//     interface CountComponent {
//         int getCount();
//     }

//     class MessageCount implements CountComponent {
//         private int count;

//         public MessageCount(int count) {
//             this.count = count;
//         }

//         public void Increment() {
//             this.count += 1;
//         }

//         @Override
//         public int getCount() {
//             return count;
//         }

//         @Override
//         public String toString() {
//             return "Message Count " + this.count;
//         }
//     }

//     //Composite Pattern usage
//     class CompositeUserCount implements CountComponent {
//         private List<CountComponent> components = new ArrayList<>();

//         public void addComponent(CountComponent component) {
//             components.add(component);
//         }

//         @Override
//         public int getCount() {
//             int total = 0;
//             for (CountComponent component : components) {
//                 total += component.getCount();
//             }
//             return total;
//         }
//     }

