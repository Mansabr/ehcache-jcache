/**
 *  Copyright 2003-2010 Terracotta, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package net.sf.ehcache.jcache;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;

import javax.cache.CacheLoader;
import java.util.Collection;
import java.util.Map;

/**
 * Adapt a {@link javax.cache.CacheLoader} to the interface of {@link net.sf.ehcache.loader.CacheLoader}
 *
 * @param <K> the type of keys used by this JCacheCacheLoaderAdapter
 * @param <V> the type of values that are loaded by this JCacheCacheLoaderAdapter
 * @author Ryan Gardner
 * @since 1.4.0-beta1
 */
public class JCacheCacheLoaderAdapter<K, V> implements net.sf.ehcache.loader.CacheLoader {
    private final javax.cache.CacheLoader<K, V> cacheLoader;

    /**
     * Construct a JCacheCacheLoaderAdapter to make {@code cacheLoader} usable via the
     * {@link net.sf.ehcache.loader.CacheLoader} interface
     *
     * @param cacheLoader the {@link CacheLoader} to adapt}
     */
    public JCacheCacheLoaderAdapter(CacheLoader<K, V> cacheLoader) {
        this.cacheLoader = cacheLoader;
    }

    /**
     * Retrieve the {@link CacheLoader} that this method wraps
     *
     * @return a {@link javax.cache.CacheLoader} object.
     */
    public final CacheLoader<K, V> getJCacheCacheLoader() {
        return cacheLoader;
    }

    /**
     * {@inheritDoc}
     *
     * loads an object. Application writers should implement this
     * method to customize the loading of cache object. This method is called
     * by the caching service when the requested object is not in the cache.
     * <p/>
     */
    @Override
    public Object load(Object key) throws CacheException {
        return cacheLoader.load((K) key);
    }

    /**
     * {@inheritDoc}
     *
     * loads multiple object. Application writers should implement this
     * method to customize the loading of cache object. This method is called
     * by the caching service when the requested object is not in the cache.
     * <p/>
     */
    @Override
    public Map loadAll(Collection keys) {
        return cacheLoader.loadAll(keys);
    }

    /**
     * {@inheritDoc}
     *
     * Load using both a key and an argument.
     * <p/>
     * JCache will call through to the load(key) method, rather than this method, where the argument is null.
     */
    @Override
    public Object load(Object key, Object argument) {
        return load(key);
    }

    /**
     * {@inheritDoc}
     *
     * Load using both a key and an argument.
     * <p/>
     * JCache will use the loadAll(key) method where the argument is null.
     */
    @Override
    public Map loadAll(Collection keys, Object argument) {
        return loadAll(keys);
    }

    /**
     * {@inheritDoc}
     *
     * Gets the name of a CacheLoader
     */
    @Override
    public String getName() {
        return this.getClass().toString();
    }

    /**
     * {@inheritDoc}
     *
     * Creates a clone of this extension. This method will only be called by ehcache before a
     * cache is initialized.
     * <p/>
     * Implementations should throw CloneNotSupportedException if they do not support clone
     * but that will stop them from being used with defaultCache.
     */
    @Override
    public net.sf.ehcache.loader.CacheLoader clone(Ehcache cache) throws CloneNotSupportedException {
        JCacheCacheLoaderAdapter clone = (JCacheCacheLoaderAdapter) super.clone();
        return clone;
    }

    /**
     * {@inheritDoc}
     *
     * Notifies providers to initialise themselves.
     * <p/>
     * This method is called during the Cache's initialise method after it has changed it's
     * status to alive. Cache operations are legal in this method.
     */
    @Override
    public void init() {
    }

    /**
     * {@inheritDoc}
     *
     * Providers may be doing all sorts of exotic things and need to be able to clean up on
     * dispose.
     * <p/>
     * Cache operations are illegal when this method is called. The cache itself is partly
     * disposed when this method is called.
     */
    @Override
    public void dispose() throws CacheException {
    }

    /** {@inheritDoc} */
    @Override
    public net.sf.ehcache.Status getStatus() {
        return null;
    }
}
