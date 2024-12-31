import Link from "./Link";

const Home = ({ data, type, setType }) => {
    return (
        <div>
            <div>
                <button onClick = {() => setType(1)}>Permanent</button>
                <button onClick = {() => setType(2)}>Temporary</button>
                <button onClick = {() => setType(3)}>One-Time</button>
            </div>
            {type === 1 && data.filter(bookmark => bookmark.type === 1).map(bookmark => (
                <Link bookmark={bookmark}/>
            ))}
            {type === 2 && data.filter(bookmark => bookmark.type === 2).map(bookmark => (
                <Link bookmark={bookmark}/>
            ))}
            {type === 3 && data.filter(bookmark => bookmark.type === 3).map(bookmark => (
                <Link bookmark={bookmark}/>
            ))}
        </div>
    );
}

export default Home;